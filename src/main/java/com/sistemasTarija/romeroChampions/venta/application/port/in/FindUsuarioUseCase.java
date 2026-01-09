package com.sistemasTarija.romeroChampions.venta.application.port.in;

import com.sistemasTarija.romeroChampions.venta.domain.model.Usuario;

public interface FindUsuarioUseCase {
    Usuario findById(Integer idUsuario);
}
