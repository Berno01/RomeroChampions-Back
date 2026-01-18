package com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.out.persistence;

import com.sistemasTarija.romeroChampions.dashboard.application.dto.StockPorCategoriaDTO;
import com.sistemasTarija.romeroChampions.dashboard.application.port.out.StockCategoriaPersistencePort;
import com.sistemasTarija.romeroChampions.dashboard.infrastructure.adapter.out.persistence.repository.StockCategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockCategoriaRepositoryAdapter implements StockCategoriaPersistencePort {
    
    private final StockCategoriaRepository repository;
    
    @Override
    public List<StockPorCategoriaDTO> obtenerStockPorCategoria(Integer idSucursal) {
        if (idSucursal == null) {
            return repository.obtenerStockPorCategoriaGlobal();
        }
        return repository.obtenerStockPorCategoria(idSucursal);
    }
    
    @Override
    public Long obtenerStockCategoria(Integer idCategoria, Integer idSucursal) {
        if (idSucursal == null) {
            return repository.obtenerStockPorCategoriaEspecificaGlobal(idCategoria);
        }
        return repository.obtenerStockPorCategoriaEspecifica(idCategoria, idSucursal);
    }
}
