package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.mapper;

import com.sistemasTarija.romeroChampions.catalogo.domain.model.Modelo;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.ModeloColor;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.ModeloColorFoto;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.Variante;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.ModeloCatalogoEntity;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.ModeloColorCatalogoEntity;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.ModeloColorFotoCatalogoEntity;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.VarianteCatalogoEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CatalogoPersistenceMapper.class})
public interface ModeloPersistenceMapper {

    @Mapping(target = "idMarca", source = "idMarca")
    @Mapping(target = "marca", ignore = true)
    @Mapping(target = "idCategoria", source = "idCategoria")
    @Mapping(target = "categoria", ignore = true)
    @Mapping(target = "idEstilo", source = "idEstilo")
    @Mapping(target = "estilo", ignore = true)
    @Mapping(target = "idGenero", source = "idGenero")
    @Mapping(target = "genero", ignore = true)
    ModeloCatalogoEntity toEntity(Modelo modelo);

    @Mapping(target = "idColor", source = "idColor")
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "modelo", ignore = true)
    ModeloColorCatalogoEntity toEntity(ModeloColor modeloColor);

    @Mapping(target = "modeloColor", ignore = true)
    @Mapping(target = "estado", constant = "true")
    ModeloColorFotoCatalogoEntity toEntity(ModeloColorFoto foto);

    @Mapping(target = "idTalla", source = "idTalla")
    @Mapping(target = "talla", ignore = true)
    @Mapping(target = "modeloColor", ignore = true)
    VarianteCatalogoEntity toEntity(Variante variante);

    // Reverse mapping
    @Mapping(target = "idMarca", source = "idMarca")
    @Mapping(target = "idCategoria", source = "idCategoria")
    @Mapping(target = "idEstilo", source = "idEstilo")
    @Mapping(target = "idGenero", source = "idGenero")
    @Mapping(target = "marca", source = "marca")
    @Mapping(target = "categoria", source = "categoria")
    @Mapping(target = "estilo", source = "estilo")
    @Mapping(target = "genero", source = "genero")
    Modelo toDomain(ModeloCatalogoEntity entity);

    @Mapping(target = "idColor", source = "idColor")
    @Mapping(target = "color", source = "color")
    ModeloColor toDomain(ModeloColorCatalogoEntity entity);

    ModeloColorFoto toDomain(ModeloColorFotoCatalogoEntity entity);

    @Mapping(target = "idTalla", source = "idTalla")
    @Mapping(target = "talla", source = "talla")
    Variante toDomain(VarianteCatalogoEntity entity);

    // Optimized Listado Mappings
    @Named("toDomainListadoModeloColor")
    @Mapping(target = "idColor", source = "idColor")
    @Mapping(target = "color", source = "color")
    @Mapping(target = "variantes", ignore = true)
    ModeloColor toDomainListado(ModeloColorCatalogoEntity entity);

    @Mapping(target = "idMarca", source = "idMarca")
    @Mapping(target = "idCategoria", source = "idCategoria")
    @Mapping(target = "idEstilo", source = "idEstilo")
    @Mapping(target = "idGenero", source = "idGenero")
    @Mapping(target = "marca", source = "marca")
    @Mapping(target = "categoria", source = "categoria")
    @Mapping(target = "estilo", source = "estilo")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "colores", qualifiedByName = "toDomainListadoModeloColor")
    Modelo toDomainListado(ModeloCatalogoEntity entity);


    @AfterMapping
    default void linkModeloColor(@MappingTarget ModeloCatalogoEntity modeloEntity) {
        if (modeloEntity.getColores() != null) {
            modeloEntity.getColores().forEach(color -> {
                color.setModelo(modeloEntity);
                linkVariante(color);
            });
        }
    }

    @AfterMapping
    default void linkVariante(@MappingTarget ModeloColorCatalogoEntity modeloColorEntity) {
        if (modeloColorEntity.getVariantes() != null) {
            modeloColorEntity.getVariantes().forEach(variante -> variante.setModeloColor(modeloColorEntity));
        }

        if (modeloColorEntity.getFotos() != null) {
            modeloColorEntity.getFotos().forEach(foto -> foto.setModeloColor(modeloColorEntity));
        }
    }
}
