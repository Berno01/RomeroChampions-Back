package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.UsuarioVentaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioVentaPersistanceMapper {

    UsuarioVentaEntity toEntity(Usuario domain);

    Usuario toDomain(UsuarioVentaEntity entity);
}
