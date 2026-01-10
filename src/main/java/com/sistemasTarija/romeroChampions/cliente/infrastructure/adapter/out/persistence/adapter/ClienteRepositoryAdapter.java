package com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.adapter;

import com.sistemasTarija.romeroChampions.cliente.application.port.out.ClientePersistencePort;
import com.sistemasTarija.romeroChampions.cliente.domain.model.Cliente;
import com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.mapper.ClientePersistenceMapper;
import com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClientePersistencePort {
    
    private final ClienteRepository repository;
    private final ClientePersistenceMapper mapper;

    @Override
    public Cliente save(Cliente cliente) {
        ClienteEntity entity = mapper.toEntity(cliente);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cliente> findAll() {
        return mapper.toDomainList(repository.findByEstadoTrue());
    }

    @Override
    public List<Cliente> search(String query) {
        return mapper.toDomainList(repository.searchByQuery(query));
    }

    @Override
    public boolean existsByNombreCompleto(String nombreCompleto) {
        return repository.existsByNombreCompletoAndEstadoTrue(nombreCompleto);
    }
}
