package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.GeneroCatalogoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GeneroRepository extends JpaRepository<GeneroCatalogoEntity, Integer> {
    Optional<GeneroCatalogoEntity> findByNombre(String nombre);
    List<GeneroCatalogoEntity> findByEstadoTrue();
}
