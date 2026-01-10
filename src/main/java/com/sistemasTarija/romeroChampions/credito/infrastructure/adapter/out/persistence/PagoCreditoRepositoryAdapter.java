package com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence;

import com.sistemasTarija.romeroChampions.credito.application.port.out.PagoCreditoPersistencePort;
import com.sistemasTarija.romeroChampions.credito.domain.model.PagoCredito;
import com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.entity.PagoCreditoEntity;
import com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.mapper.PagoCreditoPersistenceMapper;
import com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.repository.PagoCreditoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PagoCreditoRepositoryAdapter implements PagoCreditoPersistencePort {

    private final PagoCreditoRepository repository;
    private final PagoCreditoPersistenceMapper mapper;

    @Override
    public PagoCredito save(PagoCredito pago) {
        PagoCreditoEntity entity = mapper.toEntity(pago);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<PagoCredito> findById(Integer idPago) {
        return repository.findById(idPago).map(mapper::toDomain);
    }

    @Override
    public List<PagoCredito> findByVentaId(Integer idVenta) {
        return mapper.toDomainList(repository.findByIdVentaAndEstadoTrue(idVenta));
    }

    @Override
    public List<PagoCredito> findAllByFilters(Integer idSucursal, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        List<Object[]> resultados = repository.buscarConDetalles(idSucursal, fechaInicio, fechaFin);
        return resultados.stream()
                .map(obj -> {
                    PagoCreditoEntity entity = (PagoCreditoEntity) obj[0];
                    String nombreCliente = (String) obj[1];
                    String nombreSucursal = (String) obj[2];
                    Double saldoPendiente = (Double) obj[3];
                    
                    PagoCredito domain = mapper.toDomain(entity);
                    domain.setNombreCliente(nombreCliente);
                    domain.setNombreSucursal(nombreSucursal);
                    domain.setSaldoVentaActual(saldoPendiente);
                    return domain;
                })
                .toList();
    }

    @Override
    public void delete(Integer idPago) {
        // Soft delete handled in service, hard delete usually not recommended for audit
        // but if needed: repository.deleteById(idPago);
    }
}
