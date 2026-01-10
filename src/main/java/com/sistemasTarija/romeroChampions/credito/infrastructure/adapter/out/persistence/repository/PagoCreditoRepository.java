package com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.persistence.entity.PagoCreditoEntity;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PagoCreditoRepository extends JpaRepository<PagoCreditoEntity, Integer> {
    List<PagoCreditoEntity> findByIdVentaAndEstadoTrue(Integer idVenta);

    @Query("SELECT p FROM PagoCreditoEntity p, VentaEntity v " +
            "WHERE p.idVenta = v.id_venta " +
            "AND p.estado = true " +
            "AND (:idSucursal IS NULL OR v.idSucursal = :idSucursal) " +
            "AND p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<PagoCreditoEntity> buscarConFiltros(@Param("idSucursal") Integer idSucursal, 
                                             @Param("fechaInicio") LocalDateTime fechaInicio, 
                                             @Param("fechaFin") LocalDateTime fechaFin);

    @Query("SELECT p, c.nombreCompleto, s.nombre, v.saldoPendiente " +
            "FROM PagoCreditoEntity p, VentaEntity v, ClienteEntity c, SucursalEntity s " +
            "WHERE p.idVenta = v.id_venta " +
            "AND v.idCliente = c.idCliente " +
            "AND v.idSucursal = s.id " +
            "AND p.estado = true " +
            "AND (:idSucursal IS NULL OR v.idSucursal = :idSucursal) " +
            "AND p.fechaPago BETWEEN :fechaInicio AND :fechaFin")
    List<Object[]> buscarConDetalles(@Param("idSucursal") Integer idSucursal,
                                             @Param("fechaInicio") LocalDateTime fechaInicio,
                                             @Param("fechaFin") LocalDateTime fechaFin);
}
