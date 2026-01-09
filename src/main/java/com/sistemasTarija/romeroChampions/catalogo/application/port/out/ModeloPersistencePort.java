package com.sistemasTarija.romeroChampions.catalogo.application.port.out;

import com.sistemasTarija.romeroChampions.catalogo.domain.model.Modelo;

import java.util.List;
import java.util.Optional;

public interface ModeloPersistencePort {
    Modelo saveModelo(Modelo modelo);
    Optional<Modelo> findById(Integer id);
    List<Modelo> findAll();
    List<Modelo> findAllListado();
    boolean existsByNombre(String nombre);
}
