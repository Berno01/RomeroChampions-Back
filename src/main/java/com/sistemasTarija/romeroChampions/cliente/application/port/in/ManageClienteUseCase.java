package com.sistemasTarija.romeroChampions.cliente.application.port.in;

import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;

import java.util.List;

public interface ManageClienteUseCase {
    ClienteDTO createCliente(ClienteDTO dto);
    ClienteDTO updateCliente(Integer id, ClienteDTO dto);
    void deleteCliente(Integer id);
    ClienteDTO findClienteById(Integer id);
    List<ClienteDTO> findAllClientes();
    // Búsqueda por nombre/CI podría ser útil
    List<ClienteDTO> searchClientes(String query);
}
