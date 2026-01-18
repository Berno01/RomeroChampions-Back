package com.sistemasTarija.romeroChampions.dashboard.application.port.in;

import com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO;

import java.util.List;

public interface ObtenerStockCategoriaUseCase {
    
    /**
     * Obtiene el stock total agrupado por todas las categorías
     * @param idSucursal ID de la sucursal (opcional, null = todas las sucursales)
     * @return Lista de stocks por categoría
     */
    List<StockPorCategoriaDTO> obtenerStockPorCategorias(Integer idSucursal);
    
    /**
     * Obtiene el stock total de una categoría específica
     * @param idCategoria ID de la categoría
     * @param idSucursal ID de la sucursal (opcional, null = todas las sucursales)
     * @return DTO con el stock de la categoría
     */
    StockPorCategoriaDTO obtenerStockCategoria(Integer idCategoria, Integer idSucursal);
}
