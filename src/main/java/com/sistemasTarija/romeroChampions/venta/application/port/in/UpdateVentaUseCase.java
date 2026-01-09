package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.application.dto.VentaDTO;

public interface UpdateVentaUseCase {
    VentaDTO update(Integer idVenta, VentaDTO ventaDTO, Integer idUsuario);
}
