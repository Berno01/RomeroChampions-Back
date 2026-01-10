package com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.credito.domain.model.PagoCredito;
import com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.entity.PagoCreditoEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PagoCreditoPersistenceMapper {
    PagoCreditoEntity toEntity(PagoCredito domain);
    PagoCredito toDomain(PagoCreditoEntity entity);
    List<PagoCredito> toDomainList(List<PagoCreditoEntity> entities);
}
