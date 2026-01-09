package com.sistemasTarija.romeroChampions.venta.application.port.out;

import com.sistemasTarija.romeroChampions.venta.domain.model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioPersistancePort {
    Optional<Inventario> findById(Integer idInventario);
    Inventario save(Inventario idInventario);
    List<Inventario> findAll();
    Optional<Inventario> findByIdVarianteAndIdSucursal(Integer idVariante, Integer idSucursal);




}
