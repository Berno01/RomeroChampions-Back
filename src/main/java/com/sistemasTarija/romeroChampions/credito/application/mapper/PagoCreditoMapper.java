package com.sistemasTarija.romeroChampions.credito.application.mapper;

import com.sistemasTarija.romeroChampions.credito.application.dto.PagoCreditoDTO;
import com.sistemasTarija.romeroChampions.credito.domain.model.PagoCredito;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PagoCreditoMapper {

    @Mapping(target = "createdBy", source = "idUsuario")
    @Mapping(target = "updatedBy", source = "idUsuario")
    PagoCredito toDomain(PagoCreditoDTO dto);

    @Mapping(target = "idUsuario", source = "createdBy")
    PagoCreditoDTO toDto(PagoCredito domain);
}
