package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.application.dto.VentaDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaFilterDTO;

import java.util.List;
import java.util.Optional;

public interface FindVentaUseCase {
    Optional<VentaDTO> findById(Integer idVenta, Integer idUsuario);
    List<VentaDTO> findAll(VentaFilterDTO filtro, Integer idUsuario);
}
