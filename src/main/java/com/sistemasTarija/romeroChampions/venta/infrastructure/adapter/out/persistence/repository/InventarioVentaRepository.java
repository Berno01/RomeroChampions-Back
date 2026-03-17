package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.repository;

import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.InventarioVentaEntity;
import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.dto.InventarioRawDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.catalogo.ResumenPrendaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioVentaRepository extends JpaRepository<InventarioVentaEntity, Integer> {
    
    @Query("SELECT i FROM InventarioVentaEntity i WHERE i.estado = true")
    List<InventarioVentaEntity> findAllByEstadoTrue();

    @Query("SELECT i FROM InventarioVentaEntity i WHERE i.variante.id = :idVariante AND i.idSucursal = :idSucursal")
    Optional<InventarioVentaEntity> findByIdVarianteAndIdSucursal(@Param("idVariante") Integer idVariante, @Param("idSucursal") Integer idSucursal);

       @Query("SELECT v.modeloColor.modelo.costoActual FROM VarianteEntity v WHERE v.id = :idVariante")
       Optional<Double> findCostoActualByIdVariante(@Param("idVariante") Integer idVariante);

    @Query("SELECT new com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.dto.InventarioRawDTO(" +
           "m.id, m.nombre, m.precio, ma.nombre, c.nombre, e.nombre, " +
           "co.nombre, co.codigoHex, mc.codigo, mc.fotoUrl, " +
           "v.id, t.nombre, i.stockInventario) " +
           "FROM InventarioVentaEntity i " +
           "JOIN i.variante v " +
           "JOIN v.modeloColor mc " +
           "JOIN mc.modelo m " +
           "JOIN m.marca ma " +
           "JOIN m.categoria c " +
           "JOIN m.estilo e " +
           "JOIN mc.color co " +
           "JOIN v.talla t " +
           "WHERE i.idSucursal = :idSucursal AND m.id = :idModelo AND i.estado = true")
    List<InventarioRawDTO> obtenerDetalleModeloRaw(@Param("idSucursal") Integer idSucursal, @Param("idModelo") Integer idModelo);

    @Query("SELECT new com.sistemasTarija.romeroChampions.venta.application.dto.catalogo.ResumenPrendaDTO(" +
           "m.id, m.nombre, m.precio, ma.nombre, c.nombre, " +
           "(SELECT MAX(mc_sub.fotoUrl) FROM ModeloColorEntity mc_sub WHERE mc_sub.modelo.id = m.id), " +
           "SUM(i.stockInventario), " +
           "CASE WHEN SUM(i.stockInventario) < 5 THEN true ELSE false END) " +
           "FROM InventarioVentaEntity i " +
           "JOIN i.variante v " +
           "JOIN v.modeloColor mc " +
           "JOIN mc.modelo m " +
           "JOIN m.marca ma " +
           "JOIN m.categoria c " +
           "WHERE i.idSucursal = :idSucursal AND i.estado = true AND m.estado = true " +
           "GROUP BY m.id, m.nombre, m.precio, ma.nombre, c.nombre")
    List<ResumenPrendaDTO> obtenerListadoResumen(@Param("idSucursal") Integer idSucursal);
    
    /**
     * Obtiene solo los códigos de los colores de un modelo (query optimizada)
     */
    @Query("SELECT mc.codigo FROM ModeloColorEntity mc " +
           "WHERE mc.modelo.id = :idModelo " +
           "AND mc.codigo IS NOT NULL " +
           "ORDER BY mc.codigo")
    List<String> findCodigosByModeloId(@Param("idModelo") Integer idModelo);
    
    /**
     * Obtiene solo las tallas disponibles de un modelo en una sucursal (query optimizada)
     */
    @Query("SELECT DISTINCT t.nombre FROM VarianteEntity v " +
           "JOIN v.talla t " +
           "JOIN InventarioEntity i ON i.variante.id = v.id " +
           "WHERE v.modeloColor.modelo.id = :idModelo " +
           "AND i.idSucursal = :idSucursal " +
           "AND i.estado = true " +
           "ORDER BY t.nombre")
    List<String> findTallasByModeloIdAndSucursal(@Param("idModelo") Integer idModelo, @Param("idSucursal") Integer idSucursal);
}
