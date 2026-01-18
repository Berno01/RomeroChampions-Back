package com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO;
import com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.out.persistence.entity.InventarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockCategoriaRepository extends JpaRepository<InventarioEntity, Integer> {
    
    /**
     * Obtiene el stock total agrupado por categoría para una sucursal específica
     * Query optimizada con un solo JOIN
     */
    @Query("SELECT new com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO(" +
           "c.id, c.nombre, COALESCE(SUM(i.stockInventario), 0L)) " +
           "FROM DashboardInventario i " +
           "JOIN i.variante v " +
           "JOIN v.modeloColor mc " +
           "JOIN mc.modelo m " +
           "JOIN m.categoria c " +
           "WHERE i.idSucursal = :idSucursal " +
           "AND i.estado = true " +
           "GROUP BY c.id, c.nombre " +
           "ORDER BY c.nombre")
    List<StockPorCategoriaDTO> obtenerStockPorCategoria(@Param("idSucursal") Integer idSucursal);
    
    /**
     * Obtiene el stock total agrupado por categoría para todas las sucursales
     */
    @Query("SELECT new com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO(" +
           "c.id, c.nombre, COALESCE(SUM(i.stockInventario), 0L)) " +
           "FROM DashboardInventario i " +
           "JOIN i.variante v " +
           "JOIN v.modeloColor mc " +
           "JOIN mc.modelo m " +
           "JOIN m.categoria c " +
           "WHERE i.estado = true " +
           "GROUP BY c.id, c.nombre " +
           "ORDER BY c.nombre")
    List<StockPorCategoriaDTO> obtenerStockPorCategoriaGlobal();
    
    /**
     * Obtiene el stock total de una categoría específica en una sucursal
     */
    @Query("SELECT COALESCE(SUM(i.stockInventario), 0L) " +
           "FROM DashboardInventario i " +
           "JOIN i.variante v " +
           "JOIN v.modeloColor mc " +
           "JOIN mc.modelo m " +
           "WHERE m.categoria.id = :idCategoria " +
           "AND i.idSucursal = :idSucursal " +
           "AND i.estado = true")
    Long obtenerStockPorCategoriaEspecifica(@Param("idCategoria") Integer idCategoria, 
                                             @Param("idSucursal") Integer idSucursal);
    
    /**
     * Obtiene el stock total de una categoría específica en todas las sucursales
     */
    @Query("SELECT COALESCE(SUM(i.stockInventario), 0L) " +
           "FROM DashboardInventario i " +
           "JOIN i.variante v " +
           "JOIN v.modeloColor mc " +
           "JOIN mc.modelo m " +
           "WHERE m.categoria.id = :idCategoria " +
           "AND i.estado = true")
    Long obtenerStockPorCategoriaEspecificaGlobal(@Param("idCategoria") Integer idCategoria);
}
