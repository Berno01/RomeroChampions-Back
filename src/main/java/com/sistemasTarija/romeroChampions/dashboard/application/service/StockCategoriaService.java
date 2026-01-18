package com.sistemasTarija.romeroChampions.dashboard.application.service;

import com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO;
import com.sistemasTarija.romeroChampions.dashboard.application.port.in.ObtenerStockCategoriaUseCase;
import com.sistemasTarija.romeroChampions.dashboard.application.port.out.StockCategoriaPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockCategoriaService implements ObtenerStockCategoriaUseCase {
    
    private final StockCategoriaPersistencePort persistencePort;
    
    @Override
    public List<StockPorCategoriaDTO> obtenerStockPorCategorias(Integer idSucursal) {
        return persistencePort.obtenerStockPorCategoria(idSucursal);
    }
    
    @Override
    public StockPorCategoriaDTO obtenerStockCategoria(Integer idCategoria, Integer idSucursal) {
        Long stockTotal = persistencePort.obtenerStockCategoria(idCategoria, idSucursal);
        
        // Buscar la categoría en la lista general para obtener el nombre
        List<StockPorCategoriaDTO> categorias = persistencePort.obtenerStockPorCategoria(idSucursal);
        String nombreCategoria = categorias.stream()
                .filter(c -> c.getIdCategoria().equals(idCategoria))
                .findFirst()
                .map(StockPorCategoriaDTO::getNombreCategoria)
                .orElse("Categoría no encontrada");
        
        return new StockPorCategoriaDTO(idCategoria, nombreCategoria, stockTotal);
    }
}
