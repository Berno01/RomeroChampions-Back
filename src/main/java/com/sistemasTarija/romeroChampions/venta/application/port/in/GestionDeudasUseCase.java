package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.application.dto.ResumenDeudaClienteDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaCreditoPendienteDTO;

import java.util.List;

public interface GestionDeudasUseCase {
    List<ResumenDeudaClienteDTO> getResumenDeudores(Integer idUsuario);
    List<VentaCreditoPendienteDTO> getVentasPendientesPorCliente(Integer idCliente, Integer idUsuario);
}
