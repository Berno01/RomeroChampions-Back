package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.integration;

import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;
import com.sistemasTarija.romeroChampions.cliente.application.port.in.ManageClienteUseCase;
import com.sistemasTarija.romeroChampions.venta.application.port.out.ClienteIntegrationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteIntegrationAdapter implements ClienteIntegrationPort {

    private final ManageClienteUseCase manageClienteUseCase;

    @Override
    public Optional<ClienteDTO> findClienteById(Integer idCliente) {
        try {
            return Optional.ofNullable(manageClienteUseCase.findClienteById(idCliente));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
