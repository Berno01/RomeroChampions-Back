package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.domain.model.Inventario;

import java.util.List;
import java.util.Optional;

public interface InventarioUsesCases {
    List<Inventario> findAll();
    Optional<Inventario> findById(Integer idInventario);
}
