package com.sistemasTarija.romeroChampions.venta.application.service;

import com.sistemasTarija.romeroChampions.venta.application.port.in.FindUsuarioUseCase;
import com.sistemasTarija.romeroChampions.venta.application.port.out.UsuarioPersistancePort;
import com.sistemasTarija.romeroChampions.venta.domain.exception.UserFailedException;
import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioVentaService implements FindUsuarioUseCase {

    private final UsuarioPersistancePort usuarioPersistancePort;

    @Override
    public Usuario findById(Integer idUsuario) {
        return usuarioPersistancePort.findById(idUsuario)
                .orElseThrow(() -> new UserFailedException(
                        "El usuario con ID " + idUsuario + " no fue encontrado en el sistema."
                ));
    }
}
