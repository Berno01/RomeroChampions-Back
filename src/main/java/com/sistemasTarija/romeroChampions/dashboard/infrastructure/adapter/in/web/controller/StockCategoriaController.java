package com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.in.web.controller;

import com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO;
import com.sistemasTarija.romeroChampions.dashboard.application.port.in.ObtenerStockCategoriaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard/stock")
@RequiredArgsConstructor
public class StockCategoriaController {
    
    private final ObtenerStockCategoriaUseCase useCase;
    
    /**
     * GET /api/dashboard/stock/categorias
     * Obtiene el stock total agrupado por todas las categorías
     * @param idSucursal (opcional) ID de la sucursal. Si no se envía, retorna el stock global de todas las sucursales
     * @return Lista de stocks por categoría
     * 
     * Ejemplos:
     * - /api/dashboard/stock/categorias -> Stock de todas las categorías en todas las sucursales
     * - /api/dashboard/stock/categorias?idSucursal=1 -> Stock de todas las categorías en la sucursal 1
     */
    @GetMapping("/categorias")
    public ResponseEntity<List<StockPorCategoriaDTO>> obtenerStockPorCategorias(
            @RequestParam(required = false) Integer idSucursal
    ) {
        List<StockPorCategoriaDTO> stocks = useCase.obtenerStockPorCategorias(idSucursal);
        return ResponseEntity.ok(stocks);
    }
    
    /**
     * GET /api/dashboard/stock/categorias/{idCategoria}
     * Obtiene el stock total de una categoría específica
     * @param idCategoria ID de la categoría
     * @param idSucursal (opcional) ID de la sucursal. Si no se envía, retorna el stock global
     * @return Stock de la categoría
     * 
     * Ejemplos:
     * - /api/dashboard/stock/categorias/1 -> Stock de la categoría 1 en todas las sucursales
     * - /api/dashboard/stock/categorias/1?idSucursal=2 -> Stock de la categoría 1 en la sucursal 2
     */
    @GetMapping("/categorias/{idCategoria}")
    public ResponseEntity<StockPorCategoriaDTO> obtenerStockCategoria(
            @PathVariable Integer idCategoria,
            @RequestParam(required = false) Integer idSucursal
    ) {
        StockPorCategoriaDTO stock = useCase.obtenerStockCategoria(idCategoria, idSucursal);
        return ResponseEntity.ok(stock);
    }
}
