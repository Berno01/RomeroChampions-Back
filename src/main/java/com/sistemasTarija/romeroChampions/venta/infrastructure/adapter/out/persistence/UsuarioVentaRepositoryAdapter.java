package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence;

import com.sistemasTarija.romeroChampions.venta.application.port.out.UsuarioPersistancePort;
import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.mapper.UsuarioVentaPersistanceMapper;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.repository.UsuarioVentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UsuarioVentaRepositoryAdapter implements UsuarioPersistancePort {
    private final UsuarioVentaRepository repository;
    private final UsuarioVentaPersistanceMapper mapper;


    @Override
    public Optional<Usuario> findById(Integer idUsuario) {
        return repository.findById(idUsuario)
                .map(mapper::toDomain);
    }
}
