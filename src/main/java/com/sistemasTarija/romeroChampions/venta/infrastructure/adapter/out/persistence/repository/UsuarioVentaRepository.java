package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.UsuarioVentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioVentaRepository extends JpaRepository<UsuarioVentaEntity, Integer> {
    Optional<UsuarioVentaEntity> findByUsername(String username);
}
