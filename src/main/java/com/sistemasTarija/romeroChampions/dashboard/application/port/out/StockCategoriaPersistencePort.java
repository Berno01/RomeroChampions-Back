package com.sistemasTarija.romeroChampions.dashboard.application.port.out;

import com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO;

import java.util.List;

public interface StockCategoriaPersistencePort {
    
    /**
     * Obtiene el stock agrupado por categoría
     * @param idSucursal ID de la sucursal, null para todas las sucursales
     * @return Lista de stocks por categoría
     */
    List<StockPorCategoriaDTO> obtenerStockPorCategoria(Integer idSucursal);
    
    /**
     * Obtiene el stock de una categoría específica
     * @param idCategoria ID de la categoría
     * @param idSucursal ID de la sucursal, null para todas las sucursales
     * @return Stock total de la categoría
     */
    Long obtenerStockCategoria(Integer idCategoria, Integer idSucursal);
}
