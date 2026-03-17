package com.sistemasTarija.romeroChampions.venta.application.service;

import com.sistemasTarija.romeroChampions.venta.application.dto.ResumenDeudaClienteDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaCreditoPendienteDTO;
import com.sistemasTarija.romeroChampions.venta.application.port.out.ClienteIntegrationPort;
import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;

// imports
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaFilterDTO;
import com.sistemasTarija.romeroChampions.venta.application.port.in.*;
import com.sistemasTarija.romeroChampions.venta.application.port.out.InventarioPersistancePort;
import com.sistemasTarija.romeroChampions.venta.application.port.out.VentaPersistancePort;
import com.sistemasTarija.romeroChampions.venta.domain.exception.InventarioFailedExeption;
import com.sistemasTarija.romeroChampions.venta.domain.exception.UserFailedException;
import com.sistemasTarija.romeroChampions.venta.domain.exception.VentaFailedException;
import com.sistemasTarija.romeroChampions.venta.domain.model.DetalleVenta;
import com.sistemasTarija.romeroChampions.venta.domain.model.Inventario;
import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;
import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaDTO;
import com.sistemasTarija.romeroChampions.venta.application.mapper.VentaMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService implements CreateVentaUseCase, FindVentaUseCase, UpdateVentaUseCase, DeleteVentaUseCase, GestionDeudasUseCase {

    private final VentaPersistancePort ventaPort;
    private final InventarioPersistancePort inventarioPort;
    private final FindUsuarioUseCase findUsuarioUseCase;
    private final ClienteIntegrationPort clienteIntegrationPort; // Injected for name resolution
    private final VentaMapper mapper;


    @Override
    @Transactional
    public Venta save(VentaDTO ventaDTO) {
        // Asegurar que la fecha de venta sea la actual de Bolivia si viene nula o para consistencia
        if (ventaDTO.getFecha() == null) {
            ventaDTO.setFecha(LocalDateTime.now(java.time.ZoneId.of("America/La_Paz")));
        }

        if ("CREDITO".equals(ventaDTO.getTipo()) && ventaDTO.getIdCliente() == null) {
            throw new VentaFailedException("Para ventas a CREDITO, el cliente es obligatorio.");
        }

        Venta venta = mapper.toDomain(ventaDTO);
        
        // Set audit fields for creation
        venta.setCreatedAt(ventaDTO.getFecha());
        venta.setUpdatedAt(ventaDTO.getFecha());
        venta.setCreatedBy(ventaDTO.getIdUsuario());
        venta.setUpdatedBy(ventaDTO.getIdUsuario());

        for(DetalleVenta detalle : venta.getDetalleVenta()) {
            Integer idVariante = detalle.getIdVariante();
            Integer idSucursal = venta.getIdSucursal();
            aplicarCostoYGanancia(detalle);
            Optional<Inventario> optionalInventario = inventarioPort.findByIdVarianteAndIdSucursal(idVariante, idSucursal);
            Inventario inventario = optionalInventario.orElseThrow(() ->
                    new InventarioFailedExeption("La prenda con ID variante " + idVariante + " de la sucursal" + idSucursal + " no fue encontrado."));
            try{
                inventario.decreaseStock(detalle.getCantidad());
            }catch(InventarioFailedExeption e){
                throw new VentaFailedException("No se pudo completar la venta: " + e.getMessage(), e);
            }
            inventarioPort.save(inventario);
        }
        return ventaPort.save(venta);
    }

    @Override
    public Optional<VentaDTO> findById(Integer idVenta, Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);

        Integer sucursalParaBuscar = usuario.isAdmin() ? null : usuario.getIdSucursal();

        return ventaPort.findByIdAndSucursal(idVenta, sucursalParaBuscar)
                .map(venta -> {
                    VentaDTO ventaDTO = mapper.toDto(venta);
                    // Obtener el username del usuario que creó la venta
                    if (venta.getCreatedBy() != null) {
                        try {
                            Usuario creador = findUsuarioUseCase.findById(venta.getCreatedBy());
                            ventaDTO.setUsername(creador.getUsername());
                        } catch (Exception e) {
                            ventaDTO.setUsername("Usuario desconocido");
                        }
                    }
                    return ventaDTO;
                });
    }

    @Override
    public List<VentaDTO> findAll(VentaFilterDTO filtro, Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);
        Integer sucursalParaBuscar;

        if (usuario.isAdmin()) {
            sucursalParaBuscar = filtro.getIdSucursal();
        } else {
            sucursalParaBuscar = usuario.getIdSucursal();
        }

        LocalDate fechaInicioBase = (filtro.getFecha() != null) ? filtro.getFecha() : LocalDate.now();
        LocalDate fechaFinBase = (filtro.getFechaFin() != null) ? filtro.getFechaFin() : LocalDate.now();
        LocalDateTime inicio = fechaInicioBase.atStartOfDay();
        LocalDateTime fin = fechaFinBase.atTime(LocalTime.MAX);

        List<Venta> ventasDominio = ventaPort.findAllByFilters(sucursalParaBuscar, inicio, fin);
        
        // Convertir a DTO sin detalles y agregar username
        return ventasDominio.stream()
                .map(venta -> {
                    VentaDTO ventaDTO = mapper.toDtoWithoutDetails(venta);
                    // Obtener el username del usuario que creó la venta
                    if (venta.getCreatedBy() != null) {
                        try {
                            Usuario creador = findUsuarioUseCase.findById(venta.getCreatedBy());
                            ventaDTO.setUsername(creador.getUsername());
                        } catch (Exception e) {
                            ventaDTO.setUsername("Usuario desconocido");
                        }
                    }
                    return ventaDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VentaDTO update(Integer idVenta, VentaDTO ventaDTO, Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);
        Integer idSucursalBusqueda = usuario.isAdmin() ? null : usuario.getIdSucursal();

        Venta ventaAntigua = ventaPort.findByIdAndSucursal(idVenta, idSucursalBusqueda)
                .orElseThrow(() -> new VentaFailedException("Venta no encontrada o sin permisos para editarla"));

        // 1. Mapear los ítems existentes para cálculo de deltas (Key: idVariante, Value: Cantidad)
        java.util.Map<Integer, Integer> mapaStockAntiguo = ventaAntigua.getDetalleVenta().stream()
                .collect(java.util.stream.Collectors.toMap(
                        DetalleVenta::getIdVariante,
                        DetalleVenta::getCantidad,
                        Integer::sum
                ));

        if ("CREDITO".equals(ventaDTO.getTipo()) && ventaDTO.getIdCliente() == null) {
            throw new VentaFailedException("Para ventas a CREDITO, el cliente es obligatorio.");
        }

        Venta ventaNuevaDatos = mapper.toDomain(ventaDTO);

        // 2. Procesar nuevos items y calcular deltas
        for (DetalleVenta detalleNuevo : ventaNuevaDatos.getDetalleVenta()) {
            Integer idVariante = detalleNuevo.getIdVariante();
            Integer cantidadNueva = detalleNuevo.getCantidad();
            Integer idSucursal = ventaAntigua.getIdSucursal();
            aplicarCostoYGanancia(detalleNuevo);

            Inventario inventario = inventarioPort.findByIdVarianteAndIdSucursal(idVariante, idSucursal)
                    .orElseThrow(() -> new InventarioFailedExeption("Inventario no encontrado para el item ID: " + idVariante));

            if (mapaStockAntiguo.containsKey(idVariante)) {
                // El ítem ya existía -> Calcular diferencia
                Integer cantidadAntigua = mapaStockAntiguo.get(idVariante);
                int delta = cantidadNueva - cantidadAntigua;

                if (delta > 0) {
                    // Aumentó la cantidad vendida: Restar diferencia del stock
                    try {
                        inventario.decreaseStock(delta);
                    } catch (InventarioFailedExeption e) {
                        throw new VentaFailedException("Stock insuficiente para aumentar la cantidad del item " + idVariante, e);
                    }
                } else if (delta < 0) {
                    // Disminuyó la cantidad vendida: Devolver diferencia al stock
                    inventario.increaseStock(Math.abs(delta));
                }
                // Si delta es 0, no impactamos inventario

                // Marcamos como procesado
                mapaStockAntiguo.remove(idVariante);
            } else {
                // Ítem nuevo en la venta -> Restar total del stock
                try {
                    inventario.decreaseStock(cantidadNueva);
                } catch (InventarioFailedExeption e) {
                    throw new VentaFailedException("Stock insuficiente para el nuevo item " + idVariante, e);
                }
            }
            inventarioPort.save(inventario);
        }

        // 3. Procesar los ítems ELIMINADOS de la venta (quedaron en el mapa) -> Devolver todo al stock
        mapaStockAntiguo.forEach((idVariante, cantidadAntigua) -> {
            Inventario inventario = inventarioPort.findByIdVarianteAndIdSucursal(idVariante, ventaAntigua.getIdSucursal())
                    .orElseThrow(() -> new InventarioFailedExeption("Error de integridad: Inventario no encontrado para devolución del item " + idVariante));
            inventario.increaseStock(cantidadAntigua);
            inventarioPort.save(inventario);
        });

        ventaAntigua.actualizarDatos(
                ventaNuevaDatos.getDetalleVenta(),
                ventaNuevaDatos.getMontoEfectivo(),
                ventaNuevaDatos.getMontoQr(),
                ventaNuevaDatos.getMontoTarjeta(),
                ventaNuevaDatos.getDescuento(),
                ventaNuevaDatos.getTipoDescuento(),
                ventaNuevaDatos.getTipoVenta(),
                ventaNuevaDatos.getFechaLimite(),
                ventaNuevaDatos.getIdCliente()
        );
        
        // Update audit fields
        ventaAntigua.setUpdatedAt(LocalDateTime.now(java.time.ZoneId.of("America/La_Paz")));
        ventaAntigua.setUpdatedBy(ventaDTO.getIdUsuario());

        Venta ventaActualizada = ventaPort.save(ventaAntigua);
        return mapper.toDto(ventaActualizada);
    }

    @Override
    @Transactional
    public void delete(Integer idVenta, Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);
        if (!usuario.isAdmin()) {
            throw new UserFailedException("Permiso denegado: Solo los administradores pueden anular ventas.");
        }
        Venta venta = ventaPort.findByIdAndSucursal(idVenta, null)
                .orElseThrow(() -> new VentaFailedException("La venta con ID " + idVenta + " no existe."));


        if (!venta.getEstadoVenta()) {
            throw new VentaFailedException("Esta venta ya fue anulada anteriormente.");
        }

        for (DetalleVenta detalle : venta.getDetalleVenta()) {
            Inventario inventario = inventarioPort.findByIdVarianteAndIdSucursal(
                    detalle.getIdVariante(),
                    venta.getIdSucursal()
            ).orElseThrow(() -> new InventarioFailedExeption("Error: No se encontró el inventario para devolver el item " + detalle.getIdVariante()));
            inventario.increaseStock(detalle.getCantidad());
            inventarioPort.save(inventario);
        }
        venta.setEstadoVenta(false);
        ventaPort.save(venta);
    }

    @Override
    @Transactional
    public void activate(Integer idVenta, Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);
        if (!usuario.isAdmin()) {
            throw new UserFailedException("Permiso denegado: Solo los administradores pueden reactivar ventas.");
        }

        Venta venta = ventaPort.findByIdAndSucursal(idVenta, null)
                .orElseThrow(() -> new VentaFailedException("La venta con ID " + idVenta + " no existe."));

        if (venta.getEstadoVenta()) {
            throw new VentaFailedException("Esta venta ya se encuentra activa.");
        }
        for (DetalleVenta detalle : venta.getDetalleVenta()) {

            Inventario inventario = inventarioPort.findByIdVarianteAndIdSucursal(
                    detalle.getIdVariante(),
                    venta.getIdSucursal()
            ).orElseThrow(() -> new InventarioFailedExeption("Inventario no encontrado para el item " + detalle.getIdVariante()));
            try {
                inventario.decreaseStock(detalle.getCantidad());
            } catch (Exception e) {
                throw new VentaFailedException(
                        "No se puede reactivar la venta: Stock insuficiente para el item " + detalle.getIdVariante() +
                                ". Stock actual: " + inventario.getStock() + ", Requerido: " + detalle.getCantidad()
                );
            }
            inventarioPort.save(inventario);
        }
        venta.setEstadoVenta(true);
        ventaPort.save(venta);
    }

    @Override
    public List<ResumenDeudaClienteDTO> getResumenDeudores(Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);
        Integer idSucursal = usuario.isAdmin() ? null : usuario.getIdSucursal();

        List<ResumenDeudaClienteDTO> resumenes = ventaPort.findResumenDeudores(idSucursal);

        // Optimización: Si se requiere, cargar nombres de clientes.
        // Dado el requerimiento de velocidad, idéalmente esto sería un JOIN en DB.
        // Como estamos en módulos separados (lógicamente), hacemos el enriquecimiento aquí.
        // Si hay muchos, esto podría ser lento (N+1), pero es lo arquitectónicamente correcto.
        // Cachear Clientes sería la solución real si es lento.
        
        for (ResumenDeudaClienteDTO dto : resumenes) {
            clienteIntegrationPort.findClienteById(dto.getIdCliente()).ifPresent(cliente -> {
                // Adaptación: El módulo Cliente usa actualmente 'nombreCompleto' y no tiene CI separado
                dto.setNombreCompleto(cliente.getNombreCompleto());
                // dto.setCi(cliente.getCi()); // CI no disponible aún en ClienteDTO
                dto.setCi("N/A"); // Placeholder hasta que se actualice el módulo Cliente
            });
            if (dto.getNombreCompleto() == null) {
                dto.setNombreCompleto("Cliente ID: " + dto.getIdCliente());
            }
        }
        return resumenes;
    }

    @Override
    public List<VentaCreditoPendienteDTO> getVentasPendientesPorCliente(Integer idCliente, Integer idUsuario) {
        Usuario usuario = findUsuarioUseCase.findById(idUsuario);
        Integer idSucursal = usuario.isAdmin() ? null : usuario.getIdSucursal();

        return ventaPort.findVentasPendientesByCliente(idCliente, idSucursal).stream()
                .map(venta -> new VentaCreditoPendienteDTO(
                        venta.getIdVenta(),
                        venta.getFecha(),
                        venta.getFechaLimite(),
                        venta.getSaldoPendiente(),
                        venta.getDetalleVenta() != null ? venta.getDetalleVenta().size() : 0,
                        venta.getTotal() // Agregamos total original para contexto
                ))
                .collect(Collectors.toList());
    }

    private void aplicarCostoYGanancia(DetalleVenta detalle) {
        Double precioUnitario = detalle.getPrecioUnitario() != null ? detalle.getPrecioUnitario() : 0.0;
        Integer cantidad = detalle.getCantidad() != null ? detalle.getCantidad() : 0;
        Double costoUnitario = inventarioPort.findCostoActualByIdVariante(detalle.getIdVariante()).orElse(0.0);

        detalle.setCostoUnitario(costoUnitario);
        detalle.setGananciaUnitaria(precioUnitario - costoUnitario);
        detalle.setGananciaTotal((precioUnitario - costoUnitario) * cantidad);
    }
}
