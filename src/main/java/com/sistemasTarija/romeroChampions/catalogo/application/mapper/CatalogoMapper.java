package com.sistemasTarija.romeroChampions.catalogo.application.mapper;

import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.ColorDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.OptionDTO;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.options.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CatalogoMapper {

    // === MARCA ===
    public Marca toMarcaDomain(OptionDTO dto) {
        if (dto == null) return null;
        return Marca.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
    }

    public OptionDTO toMarcaDTO(Marca marca) {
        if (marca == null) return null;
        return OptionDTO.builder()
                .id(marca.getId())
                .nombre(marca.getNombre())
                .build();
    }

    public List<OptionDTO> toMarcaDTOList(List<Marca> marcas) {
        if (marcas == null) return null;
        return marcas.stream()
                .map(this::toMarcaDTO)
                .collect(Collectors.toList());
    }

    // === CATEGORIA ===
    public Categoria toCategoriaDomain(OptionDTO dto) {
        if (dto == null) return null;
        return Categoria.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
    }

    public OptionDTO toCategoriaDTO(Categoria categoria) {
        if (categoria == null) return null;
        return OptionDTO.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .build();
    }

    public List<OptionDTO> toCategoriaDTOList(List<Categoria> categorias) {
        if (categorias == null) return null;
        return categorias.stream()
                .map(this::toCategoriaDTO)
                .collect(Collectors.toList());
    }

    // === ESTILO ===
    public Estilo toEstiloDomain(OptionDTO dto) {
        if (dto == null) return null;
        return Estilo.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
    }

    public OptionDTO toEstiloDTO(Estilo estilo) {
        if (estilo == null) return null;
        return OptionDTO.builder()
                .id(estilo.getId())
                .nombre(estilo.getNombre())
                .build();
    }

    public List<OptionDTO> toEstiloDTOList(List<Estilo> estilos) {
        if (estilos == null) return null;
        return estilos.stream()
                .map(this::toEstiloDTO)
                .collect(Collectors.toList());
    }
    
    // === GENERO ===
    public Genero toGeneroDomain(OptionDTO dto) {
        if (dto == null) return null;
        return Genero.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
    }

    public OptionDTO toGeneroDTO(Genero genero) {
        if (genero == null) return null;
        return OptionDTO.builder()
                .id(genero.getId())
                .nombre(genero.getNombre())
                .build();
    }

    public List<OptionDTO> toGeneroDTOList(List<Genero> generos) {
        if (generos == null) return null;
        return generos.stream()
                .map(this::toGeneroDTO)
                .collect(Collectors.toList());
    }

    // === TALLA ===
    public Talla toTallaDomain(OptionDTO dto) {
        if (dto == null) return null;
        return Talla.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .build();
    }

    public OptionDTO toTallaDTO(Talla talla) {
        if (talla == null) return null;
        return OptionDTO.builder()
                .id(talla.getId())
                .nombre(talla.getNombre())
                .build();
    }

    public List<OptionDTO> toTallaDTOList(List<Talla> tallas) {
        if (tallas == null) return null;
        return tallas.stream()
                .map(this::toTallaDTO)
                .collect(Collectors.toList());
    }

    // === COLOR ===
    public Color toColorDomain(ColorDTO dto) {
        if (dto == null) return null;
        return Color.builder()
                .id(dto.getId())
                .nombre(dto.getNombre())
                .codigoHex(dto.getCodigoHex())
                .build();
    }

    public ColorDTO toColorDTO(Color color) {
        if (color == null) return null;
        return ColorDTO.builder()
                .id(color.getId())
                .nombre(color.getNombre())
                .codigoHex(color.getCodigoHex())
                .build();
    }

    public List<ColorDTO> toColorDTOList(List<Color> colores) {
        if (colores == null) return null;
        return colores.stream()
                .map(this::toColorDTO)
                .collect(Collectors.toList());
    }
}
