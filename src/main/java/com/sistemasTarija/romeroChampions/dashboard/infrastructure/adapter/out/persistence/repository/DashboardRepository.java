package com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.out.persistence.entity.VentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DashboardRepository extends JpaRepository<VentaEntity, Integer> {

    /**
     * KPIs del Dashboard
     */
    @Query(value = "SELECT " +
           "COALESCE(SUM(v.total_venta), 0.0) as totalVentas, " +
           "COALESCE(COUNT(v.id_venta), 0) as cantidadVentas, " +
            "COALESCE((SELECT SUM(dv.ganancia_total) " +
            "          FROM detalle_venta dv " +
            "          WHERE dv.id_venta IN (SELECT v2.id_venta " +
            "                                 FROM venta v2 " +
            "                                 WHERE v2.estado_venta = true " +
            "                                 AND (:idSucursal IS NULL OR v2.id_sucursal = :idSucursal) " +
            "                                 AND v2.fecha_venta BETWEEN :fechaInicio AND :fechaFin)), 0.0) as gananciaDevengada, " +
            "COALESCE((SELECT SUM( " +
            "          CASE " +
            "            WHEN x.valor_real_venta <= 0 THEN 0 " +
            "            WHEN x.tipo_venta = 'CONTADO' THEN x.ganancia_devengada " +
            "            WHEN x.tipo_venta = 'CREDITO' THEN x.ganancia_devengada * " +
            "                 GREATEST(0, LEAST(1, (x.valor_real_venta - COALESCE(x.saldo_pendiente, 0)) / x.valor_real_venta)) " +
            "            ELSE x.ganancia_devengada " +
            "          END) " +
            "          FROM ( " +
            "            SELECT v3.id_venta, v3.tipo_venta, COALESCE(v3.saldo_pendiente, 0) AS saldo_pendiente, " +
            "                   (COALESCE(SUM(dv3.total), 0) - COALESCE(v3.descuento, 0)) AS valor_real_venta, " +
            "                   COALESCE(SUM(dv3.ganancia_total), 0) AS ganancia_devengada " +
            "            FROM venta v3 " +
            "            LEFT JOIN detalle_venta dv3 ON dv3.id_venta = v3.id_venta " +
            "            WHERE v3.estado_venta = true " +
            "              AND (:idSucursal IS NULL OR v3.id_sucursal = :idSucursal) " +
            "              AND v3.fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
            "            GROUP BY v3.id_venta, v3.tipo_venta, v3.saldo_pendiente, v3.descuento " +
            "          ) x), 0.0) as gananciaCobrada, " +
            "( " +
            "  COALESCE((SELECT SUM(dv.ganancia_total) " +
            "            FROM detalle_venta dv " +
            "            WHERE dv.id_venta IN (SELECT v2.id_venta " +
            "                                   FROM venta v2 " +
            "                                   WHERE v2.estado_venta = true " +
            "                                   AND (:idSucursal IS NULL OR v2.id_sucursal = :idSucursal) " +
            "                                   AND v2.fecha_venta BETWEEN :fechaInicio AND :fechaFin)), 0.0) " +
            "  - " +
            "  COALESCE((SELECT SUM( " +
            "            CASE " +
            "              WHEN y.valor_real_venta <= 0 THEN 0 " +
            "              WHEN y.tipo_venta = 'CONTADO' THEN y.ganancia_devengada " +
            "              WHEN y.tipo_venta = 'CREDITO' THEN y.ganancia_devengada * " +
            "                   GREATEST(0, LEAST(1, (y.valor_real_venta - COALESCE(y.saldo_pendiente, 0)) / y.valor_real_venta)) " +
            "              ELSE y.ganancia_devengada " +
            "            END) " +
            "            FROM ( " +
            "              SELECT v4.id_venta, v4.tipo_venta, COALESCE(v4.saldo_pendiente, 0) AS saldo_pendiente, " +
            "                     (COALESCE(SUM(dv4.total), 0) - COALESCE(v4.descuento, 0)) AS valor_real_venta, " +
            "                     COALESCE(SUM(dv4.ganancia_total), 0) AS ganancia_devengada " +
            "              FROM venta v4 " +
            "              LEFT JOIN detalle_venta dv4 ON dv4.id_venta = v4.id_venta " +
            "              WHERE v4.estado_venta = true " +
            "                AND (:idSucursal IS NULL OR v4.id_sucursal = :idSucursal) " +
            "                AND v4.fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
            "              GROUP BY v4.id_venta, v4.tipo_venta, v4.saldo_pendiente, v4.descuento " +
            "            ) y), 0.0) " +
            ") as gananciaPendiente, " +
           "COALESCE((SELECT SUM(dv.cantidad) " +
           "          FROM detalle_venta dv " +
           "          WHERE dv.id_venta IN (SELECT v2.id_venta " +
           "                                 FROM venta v2 " +
           "                                 WHERE v2.estado_venta = true " +
           "                                 AND (:idSucursal IS NULL OR v2.id_sucursal = :idSucursal) " +
           "                                 AND v2.fecha_venta BETWEEN :fechaInicio AND :fechaFin)), 0) as unidadesVendidas " +
           "FROM venta v " +
           "WHERE v.estado_venta = true " +
           "AND (:idSucursal IS NULL OR v.id_sucursal = :idSucursal) " +
           "AND v.fecha_venta BETWEEN :fechaInicio AND :fechaFin",
           nativeQuery = true)
    Object obtenerKPIsRaw(
            @Param("idSucursal") Long idSucursal,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    /**
     * Ventas por hora - Cuenta cantidad de ventas por hora (no monto)
     * Sin filtro de horario - trae todas las horas donde hay ventas
     */
    @Query(value = "SELECT HOUR(v.fecha_venta) as hora, " +
           "COUNT(v.id_venta) as cantidadVentas, " +
           "COUNT(DISTINCT DATE(v.fecha_venta)) as diasUnicos " +
           "FROM venta v " +
           "WHERE v.estado_venta = true " +
           "AND (:idSucursal IS NULL OR v.id_sucursal = :idSucursal) " +
           "AND v.fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY HOUR(v.fecha_venta) " +
           "ORDER BY HOUR(v.fecha_venta)",
           nativeQuery = true)
    List<Object[]> obtenerVentasPorHoraRaw(
            @Param("idSucursal") Long idSucursal,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    /**
    /**
     * Ventas por categoría
     */
    @Query(value = "SELECT COALESCE(cat.nombre_categoria, 'Sin Categoría') as categoria, " +
           "COALESCE(SUM(dv.cantidad), 0) as cantidad " +
           "FROM venta v " +
           "JOIN detalle_venta dv ON v.id_venta = dv.id_venta " +
           "JOIN variante var ON dv.id_variante = var.id_variante " +
           "JOIN modelo_color mc ON var.id_modelo_color = mc.id_modelo_color " +
           "JOIN modelo m ON mc.id_modelo = m.id_modelo " +
           "LEFT JOIN categoria cat ON m.id_categoria = cat.id_categoria " +
           "WHERE v.estado_venta = true " +
           "AND (:idSucursal IS NULL OR v.id_sucursal = :idSucursal) " +
           "AND v.fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY cat.nombre_categoria " +
           "ORDER BY cantidad DESC",
           nativeQuery = true)
    List<Object[]> obtenerVentasPorCategoriaRaw(
            @Param("idSucursal") Long idSucursal,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );
    /**
     * Métodos de pago
     */
    @Query(value = "SELECT 'Efectivo' as metodo, COALESCE(SUM(monto_efectivo), 0) as total " +
           "FROM venta " +
           "WHERE estado_venta = true " +
           "AND (:idSucursal IS NULL OR id_sucursal = :idSucursal) " +
           "AND fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "UNION ALL " +
           "SELECT 'QR' as metodo, COALESCE(SUM(monto_qr), 0) as total " +
           "FROM venta " +
           "WHERE estado_venta = true " +
           "AND (:idSucursal IS NULL OR id_sucursal = :idSucursal) " +
           "AND fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "UNION ALL " +
           "SELECT 'Tarjeta' as metodo, COALESCE(SUM(monto_tarjeta), 0) as total " +
           "FROM venta " +
           "WHERE estado_venta = true " +
           "AND (:idSucursal IS NULL OR id_sucursal = :idSucursal) " +
           "AND fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "ORDER BY total DESC",
           nativeQuery = true)
    List<Object[]> obtenerMetodosPagoRaw(
            @Param("idSucursal") Long idSucursal,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    /**
     * Distribución de tallas
     */
    @Query(value = "SELECT COALESCE(t.nombre_talla, 'Sin Talla') as talla, " +
           "COALESCE(SUM(dv.cantidad), 0) as cantidad " +
           "FROM venta v " +
           "JOIN detalle_venta dv ON v.id_venta = dv.id_venta " +
           "JOIN variante var ON dv.id_variante = var.id_variante " +
           "LEFT JOIN talla t ON var.id_talla = t.id_talla " +
           "WHERE v.estado_venta = true " +
           "AND (:idSucursal IS NULL OR v.id_sucursal = :idSucursal) " +
           "AND v.fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY t.nombre_talla " +
           "ORDER BY cantidad DESC",
           nativeQuery = true)
    List<Object[]> obtenerDistribucionTallasRaw(
            @Param("idSucursal") Long idSucursal,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin
    );

    /**
     * Top productos más vendidos
     */
    @Query(value = "SELECT " +
           "m.nombre_modelo as nombreModelo, " +
           "CONCAT(COALESCE(cat.nombre_categoria, 'Sin Categoría'), ' - ', COALESCE(mar.nombre_marca, 'Sin Marca')) as subtitulo, " +
           "SUM(dv.cantidad) as cantidadVendida, " +
           "(SELECT mc2.foto_url FROM modelo_color mc2 WHERE mc2.id_modelo = m.id_modelo LIMIT 1) as fotoUrl, " +
           "m.id_modelo as idModelo " +
           "FROM venta v " +
           "JOIN detalle_venta dv ON v.id_venta = dv.id_venta " +
           "JOIN variante var ON dv.id_variante = var.id_variante " +
           "JOIN modelo_color mc ON var.id_modelo_color = mc.id_modelo_color " +
           "JOIN modelo m ON mc.id_modelo = m.id_modelo " +
           "LEFT JOIN categoria cat ON m.id_categoria = cat.id_categoria " +
           "LEFT JOIN marca mar ON m.id_marca = mar.id_marca " +
           "WHERE v.estado_venta = true " +
           "AND (:idSucursal IS NULL OR v.id_sucursal = :idSucursal) " +
           "AND v.fecha_venta BETWEEN :fechaInicio AND :fechaFin " +
           "GROUP BY m.id_modelo, m.nombre_modelo, cat.nombre_categoria, mar.nombre_marca " +
           "ORDER BY cantidadVendida DESC " +
           "LIMIT :limit",
           nativeQuery = true)
    List<Object[]> obtenerTopProductosRaw(
            @Param("idSucursal") Long idSucursal,
            @Param("fechaInicio") LocalDateTime fechaInicio,
            @Param("fechaFin") LocalDateTime fechaFin,
            @Param("limit") int limit
    );

    /**
     * Obtener stock actual de un modelo (suma de todas las variantes)
     */
    @Query(value = "SELECT COALESCE(SUM(i.stock_inventario), 0) " +
           "FROM inventario i " +
           "JOIN variante var ON i.id_variante = var.id_variante " +
           "JOIN modelo_color mc ON var.id_modelo_color = mc.id_modelo_color " +
           "WHERE mc.id_modelo = :idModelo " +
           "AND i.estado_inventario = true " +
           "AND (:idSucursal IS NULL OR i.id_sucursal = :idSucursal)",
           nativeQuery = true)
    Long obtenerStockActualPorModelo(
            @Param("idModelo") Integer idModelo,
            @Param("idSucursal") Long idSucursal
    );
}
