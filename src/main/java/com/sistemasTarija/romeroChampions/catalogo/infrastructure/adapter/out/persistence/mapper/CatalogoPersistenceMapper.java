package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.catalogo.domain.model.options.*;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.*;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper de Persistencia: convierte Modelos de Dominio <-> Entidades JPA
 */
@Mapper(componentModel = "spring")
public interface CatalogoPersistenceMapper {
    
    // === MARCA ===
    MarcaCatalogoEntity toMarcaEntity(Marca marca);
    Marca toMarcaDomain(MarcaCatalogoEntity entity);
    List<Marca> toMarcaDomainList(List<MarcaCatalogoEntity> entities);
    
    // === CATEGORIA ===
    CategoriaCatalogoEntity toCategoriaEntity(Categoria categoria);
    Categoria toCategoriaDomain(CategoriaCatalogoEntity entity);
    List<Categoria> toCategoriaDomainList(List<CategoriaCatalogoEntity> entities);
    
    // === ESTILO ===
    EstiloCatalogoEntity toEstiloEntity(Estilo estilo);
    Estilo toEstiloDomain(EstiloCatalogoEntity entity);
    List<Estilo> toEstiloDomainList(List<EstiloCatalogoEntity> entities);
    
    // === GENERO ===
    GeneroCatalogoEntity toGeneroEntity(Genero genero);
    Genero toGeneroDomain(GeneroCatalogoEntity entity);
    List<Genero> toGeneroDomainList(List<GeneroCatalogoEntity> entities);
    
    // === TALLA ===
    TallaCatalogoEntity toTallaEntity(Talla talla);
    Talla toTallaDomain(TallaCatalogoEntity entity);
    List<Talla> toTallaDomainList(List<TallaCatalogoEntity> entities);
    
    // === COLOR ===
    ColorCatalogoEntity toColorEntity(Color color);
    Color toColorDomain(ColorCatalogoEntity entity);
    List<Color> toColorDomainList(List<ColorCatalogoEntity> entities);
}
