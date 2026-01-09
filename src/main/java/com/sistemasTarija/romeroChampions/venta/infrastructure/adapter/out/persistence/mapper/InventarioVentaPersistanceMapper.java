package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.venta.domain.model.Inventario;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.InventarioVentaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InventarioVentaPersistanceMapper {

    @Mapping(target = "stockInventario", source = "stock")
    @Mapping(target = "variante.id", source = "idVariante")
    InventarioVentaEntity toInventarioEntity(Inventario inventario);


    @Mapping(target = "stock", source = "stockInventario")
    @Mapping(target = "idVariante", source = "variante.id")
    Inventario toInventarioDomain(InventarioVentaEntity inventarioEntity);

    List<Inventario> toInventarioList(List<InventarioVentaEntity> entityList);
}
