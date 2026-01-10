package com.sistemasTarija.romeroChampions.venta.application.port.out;

import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;
import java.util.Optional;

public interface ClienteIntegrationPort {
    Optional<ClienteDTO> findClienteById(Integer idCliente);
}
