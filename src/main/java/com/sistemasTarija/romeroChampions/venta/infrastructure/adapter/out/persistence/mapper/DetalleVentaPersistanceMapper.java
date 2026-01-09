package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.venta.domain.model.DetalleVenta;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.DetalleVentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleVentaPersistanceMapper {

    // Mapeo Domain -> Entity (para escritura)
    // La ID se genera en la DB
    // idModelo se ignora porque no existe en la entidad
    @Mapping(target = "id_detalle_venta", ignore = true)
    @Mapping(target = "venta", ignore = true)
    @Mapping(target = "variante", ignore = true)
    DetalleVentaEntity toDetalleEntity(DetalleVenta detalle);

    // Mapeo Entity -> Domain (para lectura)
    // Extrae idModelo desde la relación profunda: variante.modeloColor.modelo.id
    @Mapping(target = "idModelo", source = "variante.modeloColor.modelo.id")
    DetalleVenta toDetalleModel(DetalleVentaEntity entity);

}

