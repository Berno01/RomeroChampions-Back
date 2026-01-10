package com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.cliente.domain.model.Cliente;
import com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClientePersistenceMapper {
    
    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        return Cliente.builder()
                .idCliente(entity.getIdCliente())
                .nombreCompleto(entity.getNombreCompleto())
                .celular(entity.getCelular())
                .lugarTrabajo(entity.getLugarTrabajo())
                .direccionCasa(entity.getDireccionCasa())
                .registradoPor(entity.getRegistradoPor())
                .estado(entity.getEstado())
                .createdAt(entity.getCreatedAt())
                .build();
    }
    
    public ClienteEntity toEntity(Cliente domain) {
        if (domain == null) return null;
        ClienteEntity entity = new ClienteEntity();
        entity.setIdCliente(domain.getIdCliente());
        entity.setNombreCompleto(domain.getNombreCompleto());
        entity.setCelular(domain.getCelular());
        entity.setLugarTrabajo(domain.getLugarTrabajo());
        entity.setDireccionCasa(domain.getDireccionCasa());
        entity.setRegistradoPor(domain.getRegistradoPor());
        entity.setEstado(domain.getEstado());
        entity.setCreatedAt(domain.getCreatedAt());
        return entity;
    }
    
    public List<Cliente> toDomainList(List<ClienteEntity> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
