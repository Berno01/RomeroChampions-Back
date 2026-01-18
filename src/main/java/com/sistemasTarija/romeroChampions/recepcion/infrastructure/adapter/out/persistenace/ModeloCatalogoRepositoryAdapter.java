package com.sistemasTarija.romeroChampions.recepcion.infrastructure.adapter.out.persistenace;

import com.sistemasTarija.romeroChampions.recepcion.application.dto.catalogo.ResumenModeloDTO;
import com.sistemasTarija.romeroChampions.recepcion.application.port.out.ModeloCatalogoPersistancePort;
import com.sistemasTarija.romeroChampions.recepcion.infrastructure.adapter.out.persistenace.dto.ModeloRawDTO;
import com.sistemasTarija.romeroChampions.recepcion.infrastructure.adapter.out.persistenace.repository.ModeloCatalogoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ModeloCatalogoRepositoryAdapter implements ModeloCatalogoPersistancePort {
    
    private final ModeloCatalogoRepository repository;

    @Override
    public List<ResumenModeloDTO> obtenerListadoModelos() {
        List<ResumenModeloDTO> resumen = repository.obtenerListadoModelos();
        
        // Poblar los códigos de cada modelo con una query adicional eficiente
        resumen.forEach(dto -> {
            List<String> codigos = repository.findCodigosByModeloId(dto.getIdModelo());
            dto.setCodigos(codigos);
        });
        
        return resumen;
    }

    @Override
    public List<ModeloRawDTO> obtenerDetalleModeloRaw(Integer idModelo) {
        return repository.obtenerDetalleModeloRaw(idModelo);
    }
}
