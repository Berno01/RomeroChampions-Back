package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", uses = {DetalleVentaPersistanceMapper.class})
public interface VentaPersistanceMapper {

    @Mapping(target = "id_venta", source = "idVenta")
    VentaEntity toVentaEntity(Venta venta);


    @Mapping(target = "idVenta", source = "id_venta")
    Venta toVentaModel(VentaEntity entity);


    List<Venta> toListVentaModel(List<VentaEntity> entityList);

}
