package com.sistemasTarija.romeroChampions.recepcion.application.port.in;

public interface DeleteRecepcionUseCase {
    void anularRecepcion(Integer idRecepcion, Integer idUsuario);
}
