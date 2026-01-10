package com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Integer> {
    
    List<ClienteEntity> findByEstadoTrue();
    
    boolean existsByNombreCompletoAndEstadoTrue(String nombreCompleto);

    @Query("SELECT c FROM ClienteEntity c WHERE c.estado = true AND (LOWER(c.nombreCompleto) LIKE LOWER(CONCAT('%', :query, '%')) OR c.celular LIKE %:query%)")
    List<ClienteEntity> searchByQuery(@Param("query") String query);
}
