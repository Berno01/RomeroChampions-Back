package com.sistemasTarija.romeroChampions.credito.application.service;

import com.sistemasTarija.romeroChampions.credito.application.dto.PagoCreditoDTO;
import com.sistemasTarija.romeroChampions.credito.application.dto.PagoFilterDTO;
import com.sistemasTarija.romeroChampions.credito.application.mapper.PagoCreditoMapper;
import com.sistemasTarija.romeroChampions.credito.application.port.in.ManagePagoUseCase;
import com.sistemasTarija.romeroChampions.credito.application.port.out.PagoCreditoPersistencePort;
import com.sistemasTarija.romeroChampions.credito.application.port.out.VentaIntegrationPort;
import com.sistemasTarija.romeroChampions.credito.domain.exception.CreditoFailedException;
import com.sistemasTarija.romeroChampions.credito.domain.model.PagoCredito;
import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;
import com.sistemasTarija.romeroChampions.venta.application.port.in.FindUsuarioUseCase;
import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoCreditoService implements ManagePagoUseCase {

    private final PagoCreditoPersistencePort pagoPort;
    private final VentaIntegrationPort ventaPort;
    private final FindUsuarioUseCase findUsuarioUseCase; // Necesitamos esto para sacar la sucursal del usuario
    private final PagoCreditoMapper mapper;

    @Override
    @Transactional
    public PagoCreditoDTO createPayment(PagoCreditoDTO dto) {
        // 1. Obtener Venta
        Venta venta = ventaPort.findVentaById(dto.getIdVenta())
                .orElseThrow(() -> new CreditoFailedException("Venta no encontrada: " + dto.getIdVenta()));

        // 2. Validaciones básicas
        if (dto.getMontoPago() == null || dto.getMontoPago() <= 0) {
            throw new CreditoFailedException("El monto del pago debe ser mayor a 0.");
        }
        
        // Use a small epsilon for floating point comparison if needed, or precise checking
        if (dto.getMontoPago() > venta.getSaldoPendiente()) {
             throw new CreditoFailedException("El monto del pago (" + dto.getMontoPago() + 
                     ") excede el saldo pendiente (" + venta.getSaldoPendiente() + ").");
        }

        // 3. Crear modelo de dominio
        PagoCredito pago = mapper.toDomain(dto);
        pago.setFechaPago(LocalDateTime.now(java.time.ZoneId.of("America/La_Paz")));
        pago.setEstado(true);
        pago.setCreatedAt(pago.getFechaPago());
        pago.setUpdatedAt(pago.getFechaPago());

        // 4. Actualizar Venta (Saldo Pendiente)
        double nuevoSaldo = venta.getSaldoPendiente() - pago.getMontoPago(); 
        if (nuevoSaldo < 0) nuevoSaldo = 0.0;
        
        venta.setSaldoPendiente(nuevoSaldo);
        
        // 5. Guardar atomicamente (Transaccional)
        ventaPort.updateVenta(venta);
        PagoCredito savedPago = pagoPort.save(pago);

        return mapper.toDto(savedPago);
    }

    @Override
    @Transactional
    public PagoCreditoDTO updatePayment(Integer idPago, PagoCreditoDTO dto) {
        // 1. Obtener Pago Existente
        PagoCredito pagoExistente = pagoPort.findById(idPago)
                .orElseThrow(() -> new CreditoFailedException("Pago no encontrado: " + idPago));

        if (!pagoExistente.getEstado()) {
            throw new CreditoFailedException("No se puede editar un pago anulado.");
        }

        // 2. Obtener Venta
        Venta venta = ventaPort.findVentaById(pagoExistente.getIdVenta())
                .orElseThrow(() -> new CreditoFailedException("Venta asociada no encontrada."));

        // 3. Calcular diferencia (Nuevo - Viejo)
        Double montoViejo = pagoExistente.getMontoPago();
        Double nuevoMonto = dto.getMontoPago();

        if (nuevoMonto == null || nuevoMonto <= 0) {
            throw new CreditoFailedException("El nuevo monto debe ser positivo.");
        }

        // Validación: El saldo no puede quedar negativo.
        // SaldoActual + MontoViejo (lo que había antes del pago) >= NuevoMonto
        if ((venta.getSaldoPendiente() + montoViejo) < nuevoMonto) {
             throw new CreditoFailedException("El nuevo monto excede el saldo pendiente original.");
        }

        // 4. Actualizar Saldo Venta
        // Logica: Saldo = Saldo + Viejo - Nuevo
        // Diferencia neta a restar al saldo actual: (Nuevo - Viejo)
        // Ejemplo: Saldo 50. Pago Viejo 10. Nuevo Pago 15. Diferencia +5. Saldo Nuevo = 50 - 5 = 45.
        // Ejemplo: Saldo 50. Pago Viejo 15. Nuevo Pago 10. Diferencia -5. Saldo Nuevo = 50 - (-5) = 55.
        double diferencia = nuevoMonto - montoViejo;
        double nuevoSaldoVenta = venta.getSaldoPendiente() - diferencia;
        
        if (nuevoSaldoVenta < 0) nuevoSaldoVenta = 0.0;
        venta.setSaldoPendiente(nuevoSaldoVenta);

        // 5. Actualizar Pago
        pagoExistente.setMontoPago(nuevoMonto);
        pagoExistente.setMetodoPago(dto.getMetodoPago());
        pagoExistente.setObservacion(dto.getObservacion());
        pagoExistente.setUpdatedAt(LocalDateTime.now(java.time.ZoneId.of("America/La_Paz")));
        pagoExistente.setUpdatedBy(dto.getIdUsuario());

        // 6. Persistir cambios
        ventaPort.updateVenta(venta);
        PagoCredito savedPago = pagoPort.save(pagoExistente);

        return mapper.toDto(savedPago);
    }

    @Override
    @Transactional
    public void deletePayment(Integer idPago, Integer idUsuario) {
        // ... same implementation ...
        PagoCredito pago = pagoPort.findById(idPago)
                .orElseThrow(() -> new CreditoFailedException("Pago no encontrado."));

        if (!pago.getEstado()) {
             throw new CreditoFailedException("El pago ya está anulado.");
        }

        Venta venta = ventaPort.findVentaById(pago.getIdVenta())
                .orElseThrow(() -> new CreditoFailedException("Venta no encontrada."));

        Double nuevoSaldo = venta.getSaldoPendiente() + pago.getMontoPago();
        
        venta.setSaldoPendiente(nuevoSaldo);

        pago.setEstado(false);
        pago.setUpdatedAt(LocalDateTime.now(java.time.ZoneId.of("America/La_Paz")));
        pago.setUpdatedBy(idUsuario);

        ventaPort.updateVenta(venta);
        pagoPort.save(pago);
    }

    @Override
    public List<PagoCreditoDTO> getPaymentsByVenta(Integer idVenta) {
        return pagoPort.findByVentaId(idVenta).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PagoCreditoDTO> getPaymentsByFilter(PagoFilterDTO filtro, Integer idUsuario) {
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

        return pagoPort.findAllByFilters(sucursalParaBuscar, inicio, fin).stream()
                .map(pago -> {
                    PagoCreditoDTO dto = mapper.toDto(pago);
                    // Opcional: Agregar info extra si se requiere
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
