package com.sistemasTarija.romeroChampions.recepcion.application.port.in;

import com.sistemasTarija.romeroChampions.recepcion.application.dto.RecepcionDTO;
import com.sistemasTarija.romeroChampions.recepcion.domain.model.Recepcion;

public interface UpdateRecepcionUseCase {
    Recepcion update(Integer idRecepcion, RecepcionDTO recepcionDTO, Integer idUsuario);
}
