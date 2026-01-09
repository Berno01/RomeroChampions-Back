package com.sistemasTarija.romeroChampions.inventario.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.inventario.domain.model.*;
import com.sistemasTarija.romeroChampions.inventario.domain.model.options.*;
import com.sistemasTarija.romeroChampions.inventario.infrastructure.adapter.out.persistence.entity.modelo.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModeloPersistenceMapper {
    
    public Modelo toDomain(ModeloInventarioEntity entity, List<ModeloColorInventarioEntity> coloresEntities) {
        if (entity == null) {
            return null;
        }
        
        Modelo modelo = Modelo.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .idMarca(entity.getMarca() != null ? entity.getMarca().getId() : null)
                .idCategoria(entity.getCategoria() != null ? entity.getCategoria().getId() : null)
                .idEstilo(entity.getEstilo() != null ? entity.getEstilo().getId() : null)
                .estado(entity.getEstado())
                .build();
        
        // Mapear objetos completos
        if (entity.getMarca() != null) {
            modelo.setMarca(marcaToDomain(entity.getMarca()));
        }
        if (entity.getCategoria() != null) {
            modelo.setCategoria(categoriaToDomain(entity.getCategoria()));
        }
        if (entity.getEstilo() != null) {
            modelo.setEstilo(estiloToDomain(entity.getEstilo()));
        }
        
        // Mapear colores si están presentes
        if (coloresEntities != null && !coloresEntities.isEmpty()) {
            List<ModeloColor> colores = coloresEntities.stream()
                    .map(this::modeloColorToDomain)
                    .collect(Collectors.toList());
            modelo.setColores(colores);
        }
        
        return modelo;
    }
    
    public ModeloColor modeloColorToDomain(ModeloColorInventarioEntity entity) {
        if (entity == null) {
            return null;
        }
        
        ModeloColor modeloColor = ModeloColor.builder()
                .id(entity.getId())
                .fotoUrl(entity.getFotoUrl())
                .idColor(entity.getColor() != null ? entity.getColor().getId() : null)
                .variantes(new ArrayList<>())
                .build();
        
        if (entity.getColor() != null) {
            modeloColor.setColor(colorToDomain(entity.getColor()));
        }
        
        return modeloColor;
    }
    
    public Variante varianteToDomain(VarianteInventarioEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Variante variante = Variante.builder()
                .id(entity.getId())
                .idTalla(entity.getTalla() != null ? entity.getTalla().getId() : null)
                .build();
        
        if (entity.getTalla() != null) {
            variante.setTalla(tallaToDomain(entity.getTalla()));
        }
        
        return variante;
    }
    
    private Marca marcaToDomain(MarcaInventarioEntity entity) {
        if (entity == null) return null;
        return Marca.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .estado(entity.getEstado())
                .build();
    }
    
    private Categoria categoriaToDomain(CategoriaInventarioEntity entity) {
        if (entity == null) return null;
        return Categoria.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .estado(entity.getEstado())
                .build();
    }
    
    private Estilo estiloToDomain(EstiloInventarioEntity entity) {
        if (entity == null) return null;
        return Estilo.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .estado(entity.getEstado())
                .build();
    }
    
    private Color colorToDomain(ColorInventarioEntity entity) {
        if (entity == null) return null;
        return Color.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .codigoHex(entity.getCodigoHex())
                .estado(entity.getEstado())
                .build();
    }
    
    private Talla tallaToDomain(TallaInventarioEntity entity) {
        if (entity == null) return null;
        return Talla.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .estado(entity.getEstado())
                .build();
    }
}
