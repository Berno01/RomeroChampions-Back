package com.sistemasTarija.romeroChampions.venta.application.port.out;

import com.sistemasTarija.romeroChampions.venta.application.dto.catalogo.ResumenPrendaDTO;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.dto.InventarioRawDTO;

import java.util.List;

public interface InventarioCatalogoVentaPersistancePort {

    List<InventarioRawDTO> obtenerDetalleModeloRaw(Integer idSucursal, Integer idModelo);
    List<ResumenPrendaDTO> obtenerListadoResumen(Integer idSucursal);
}
