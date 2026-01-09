package com.sistemasTarija.romeroChampions.venta.application.port.out;

import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;

import java.util.Optional;

public interface UsuarioPersistancePort {
    Optional<Usuario> findById(Integer idUsuario);
}
