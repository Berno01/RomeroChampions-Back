package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<VentaEntity, Integer> {

    @Query("SELECT v FROM VentaEntity v WHERE v.id_venta = :idVenta AND (:idSucursal IS NULL OR v.idSucursal = :idSucursal)")
    Optional<VentaEntity> buscarPorIdYSucursal(@Param("idVenta") Integer idVenta, @Param("idSucursal") Integer idSucursal);

    @Query("SELECT v FROM VentaEntity v WHERE (:idSucursal IS NULL OR v.idSucursal = :idSucursal) AND v.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<VentaEntity> buscarListaConFiltros(@Param("idSucursal") Integer idSucursal, @Param("fechaInicio") LocalDateTime fechaInicio, @Param("fechaFin") LocalDateTime fechaFin);

    // Consulta agregada para deudores: Agrupa por cliente y suma saldos pendientes positivos
    @Query("SELECT v.idCliente, COUNT(v), SUM(v.saldoPendiente), MIN(v.fechaLimite) " +
           "FROM VentaEntity v " +
           "WHERE v.saldoPendiente > 0 " +
           "AND v.estadoVenta = true " + // Solo ventas activas
           "AND (:idSucursal IS NULL OR v.idSucursal = :idSucursal) " +
           "GROUP BY v.idCliente")
    List<Object[]> encontrarResumenDeudores(@Param("idSucursal") Integer idSucursal);

    // Consulta para listar ventas pendientes de un cliente específico
    @Query("SELECT v FROM VentaEntity v " +
           "WHERE v.idCliente = :idCliente " +
           "AND v.saldoPendiente > 0 " +
           "AND v.estadoVenta = true " +
           "AND (:idSucursal IS NULL OR v.idSucursal = :idSucursal) " +
           "ORDER BY v.fechaLimite ASC")
    List<VentaEntity> encontrarVentasPendientesPorCliente(@Param("idCliente") Integer idCliente, @Param("idSucursal") Integer idSucursal);
}
