package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence;

import com.sistemasTarija.romeroChampions.venta.application.dto.catalogo.ResumenPrendaDTO;
import com.sistemasTarija.romeroChampions.venta.application.port.out.InventarioCatalogoVentaPersistancePort;
import com.sistemasTarija.romeroChampions.venta.application.port.out.InventarioPersistancePort;
import com.sistemasTarija.romeroChampions.venta.domain.model.Inventario;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.dto.InventarioRawDTO;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.mapper.InventarioVentaPersistanceMapper;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.repository.InventarioVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class InventarioVentaRepositoryAdapter implements InventarioPersistancePort, InventarioCatalogoVentaPersistancePort {
    private final InventarioVentaPersistanceMapper mapper;
    private final InventarioVentaRepository repository;

    @Override
    public Optional<Inventario> findById(Integer idInventario) {
        return Optional.empty();
    }

    @Override
    public Inventario save(Inventario inventario) {
        return mapper.toInventarioDomain(repository.save(mapper.toInventarioEntity(inventario)));
    }

    @Override
    public List<Inventario> findAll() {
        return mapper.toInventarioList(repository.findAllByEstadoTrue());
    }

    @Override
    public Optional<Inventario> findByIdVarianteAndIdSucursal(Integer idVariante, Integer idSucursal) {
        return repository.findByIdVarianteAndIdSucursal(idVariante,idSucursal).map(mapper::toInventarioDomain);
    }

    @Override
    public Optional<Double> findCostoActualByIdVariante(Integer idVariante) {
        return repository.findCostoActualByIdVariante(idVariante);
    }

    @Override
    public List<InventarioRawDTO> obtenerDetalleModeloRaw(Integer idSucursal, Integer idModelo) {
        return repository.obtenerDetalleModeloRaw(idSucursal, idModelo);
    }

    @Override
    public List<ResumenPrendaDTO> obtenerListadoResumen(Integer idSucursal) {
        List<ResumenPrendaDTO> resumen = repository.obtenerListadoResumen(idSucursal);
        
        // Poblar los códigos y tallas de cada modelo con queries adicionales eficientes
        resumen.forEach(dto -> {
            List<String> codigos = repository.findCodigosByModeloId(dto.getIdModelo());
            dto.setCodigos(codigos);
            
            List<String> tallas = repository.findTallasByModeloIdAndSucursal(dto.getIdModelo(), idSucursal);
            dto.setTallas(tallas);
        });
        
        return resumen;
    }
}
