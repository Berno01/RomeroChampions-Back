package com.sistemasTarija.romeroChampions.cliente.application.service;

import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;
import com.sistemasTarija.romeroChampions.cliente.application.mapper.ClienteMapper;
import com.sistemasTarija.romeroChampions.cliente.application.port.in.ManageClienteUseCase;
import com.sistemasTarija.romeroChampions.cliente.application.port.out.ClientePersistencePort;
import com.sistemasTarija.romeroChampions.cliente.domain.exception.ClienteFailedException;
import com.sistemasTarija.romeroChampions.cliente.domain.model.Cliente;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService implements ManageClienteUseCase {

    private final ClientePersistencePort persistencePort;
    private final ClienteMapper mapper;

    @Override
    public ClienteDTO createCliente(ClienteDTO dto) {
        // Validar unicidad? Quizás por celular o nombre exacto sea muy restrictivo, pero revisamos nombre
        // Dejamos flexible por ahora, dos Juan Perez pueden existir.
        
        Cliente domain = mapper.toDomain(dto);
        domain.setEstado(true);
        domain.setCreatedAt(LocalDateTime.now());
        
        Cliente saved = persistencePort.save(domain);
        return mapper.toDto(saved);
    }

    @Override
    public ClienteDTO updateCliente(Integer id, ClienteDTO dto) {
        Cliente existing = persistencePort.findById(id)
                .orElseThrow(() -> new ClienteFailedException("Cliente no encontrado con id: " + id));
        
        // Actualizar campos
        existing.setNombreCompleto(dto.getNombreCompleto());
        existing.setCelular(dto.getCelular());
        existing.setLugarTrabajo(dto.getLugarTrabajo());
        existing.setDireccionCasa(dto.getDireccionCasa());
        // No actualizamos creadoPor o createdAt usualmente
        
        Cliente updated = persistencePort.save(existing);
        return mapper.toDto(updated);
    }

    @Override
    public void deleteCliente(Integer id) {
        Cliente existing = persistencePort.findById(id)
                .orElseThrow(() -> new ClienteFailedException("Cliente no encontrado con id: " + id));
        
        existing.setEstado(false); // Soft delete
        persistencePort.save(existing);
    }

    @Override
    public ClienteDTO findClienteById(Integer id) {
        return persistencePort.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ClienteFailedException("Cliente no encontrado con id: " + id));
    }

    @Override
    public List<ClienteDTO> findAllClientes() {
        return mapper.toDtoList(persistencePort.findAll());
    }

    @Override
    public List<ClienteDTO> searchClientes(String query) {
        return mapper.toDtoList(persistencePort.search(query));
    }
}
