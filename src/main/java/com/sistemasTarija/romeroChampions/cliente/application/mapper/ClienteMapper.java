package com.sistemasTarija.romeroChampions.cliente.application.mapper;

import com.sistemasTarija.romeroChampions.cliente.application.dto.ClienteDTO;
import com.sistemasTarija.romeroChampions.cliente.domain.model.Cliente;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteMapper {
    
    public Cliente toDomain(ClienteDTO dto) {
        if (dto == null) return null;
        return Cliente.builder()
                .idCliente(dto.getIdCliente())
                .nombreCompleto(dto.getNombreCompleto())
                .celular(dto.getCelular())
                .lugarTrabajo(dto.getLugarTrabajo())
                .direccionCasa(dto.getDireccionCasa())
                .registradoPor(dto.getRegistradoPor())
                .createdAt(dto.getCreatedAt())
                // estado se maneja en capa de persistencia o servicio por defecto a true
                .build();
    }
    
    public ClienteDTO toDto(Cliente domain) {
        if (domain == null) return null;
        return ClienteDTO.builder()
                .idCliente(domain.getIdCliente())
                .nombreCompleto(domain.getNombreCompleto())
                .celular(domain.getCelular())
                .lugarTrabajo(domain.getLugarTrabajo())
                .direccionCasa(domain.getDireccionCasa())
                .registradoPor(domain.getRegistradoPor())
                .createdAt(domain.getCreatedAt())
                .build();
    }
    
    public List<ClienteDTO> toDtoList(List<Cliente> clientes) {
        if (clientes == null) return List.of();
        return clientes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}
