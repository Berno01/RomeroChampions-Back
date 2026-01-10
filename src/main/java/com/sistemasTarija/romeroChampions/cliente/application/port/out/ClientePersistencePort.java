package com.sistemasTarija.romeroChampions.cliente.application.port.out;

import com.sistemasTarija.romeroChampions.cliente.domain.model.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClientePersistencePort {
    Cliente save(Cliente cliente);
    Optional<Cliente> findById(Integer id);
    List<Cliente> findAll();
    List<Cliente> search(String query); // Buscar por nombre o atributos parciales
    boolean existsByNombreCompleto(String nombreCompleto);
}
