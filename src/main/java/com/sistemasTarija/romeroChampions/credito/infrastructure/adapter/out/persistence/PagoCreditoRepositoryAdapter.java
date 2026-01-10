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
        return mapper.toDomainList(repository.buscarConFiltros(idSucursal, fechaInicio, fechaFin));
    }

    @Override
    public void delete(Integer idPago) {
        // Soft delete handled in service, hard delete usually not recommended for audit
        // but if needed: repository.deleteById(idPago);
    }
}
