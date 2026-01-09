package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.application.dto.catalogo.DetallePrendaDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.catalogo.ResumenPrendaDTO;

import java.util.List;

public interface FindCatalogoUseCase {

    List<ResumenPrendaDTO> getListadoGeneral(Integer idSucursal);
    DetallePrendaDTO getDetalleModelo(Integer idModelo, Integer idSucursal);
}
