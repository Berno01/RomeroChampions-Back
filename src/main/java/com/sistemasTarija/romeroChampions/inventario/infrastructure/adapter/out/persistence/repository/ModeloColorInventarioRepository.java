package com.sistemasTarija.romeroChampions.inventario.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.inventario.infrastructure.adapter.out.persistence.entity.modelo.ModeloColorInventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloColorInventarioRepository extends JpaRepository<ModeloColorInventarioEntity, Integer> {
    
    /**
     * Busca todos los colores de un modelo con sus variantes
     */
    @Query("SELECT DISTINCT mc FROM ModeloColorInventarioEntity mc " +
            "LEFT JOIN FETCH mc.color " +
            "WHERE mc.modelo.id = :idModelo")
    List<ModeloColorInventarioEntity> findByModeloIdWithColor(@Param("idModelo") Integer idModelo);
    
    /**
     * Obtiene solo los códigos de los colores de un modelo (query optimizada)
     */
    @Query("SELECT mc.codigo FROM ModeloColorInventarioEntity mc " +
            "WHERE mc.modelo.id = :idModelo " +
            "AND mc.codigo IS NOT NULL " +
            "ORDER BY mc.codigo")
    List<String> findCodigosByModeloId(@Param("idModelo") Integer idModelo);
}
