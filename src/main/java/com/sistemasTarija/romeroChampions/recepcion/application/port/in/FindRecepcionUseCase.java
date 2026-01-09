package com.sistemasTarija.romeroChampions.recepcion.application.port.in;

import com.sistemasTarija.romeroChampions.recepcion.application.dto.RecepcionDTO;
import com.sistemasTarija.romeroChampions.recepcion.application.dto.RecepcionFilterDTO;

import java.util.List;
import java.util.Optional;

public interface FindRecepcionUseCase {
    Optional<RecepcionDTO> findById(Integer idRecepcion, Integer idUsuario);
    List<RecepcionDTO> findAll(RecepcionFilterDTO filtro, Integer idUsuario);
}
