package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaDTO;

public interface CreateVentaUseCase {
    Venta save(VentaDTO ventaDTO);
}
