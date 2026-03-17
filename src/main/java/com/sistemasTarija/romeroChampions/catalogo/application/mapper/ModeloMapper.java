package com.sistemasTarija.romeroChampions.catalogo.application.mapper;

import com.sistemasTarija.romeroChampions.catalogo.application.dto.ModeloDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.ModeloListadoDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.RegistrarModeloRequest;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.Modelo;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.ModeloColor;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.ModeloColorFoto;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.Variante;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ModeloMapper {

    private final CatalogoMapper catalogoMapper;

    public Modelo toDomain(RegistrarModeloRequest request) {
        if (request == null) return null;

        List<ModeloColor> colores = new ArrayList<>();
        if (request.getColores() != null) {
            colores = request.getColores().stream()
                    .map(this::toModeloColorDomain)
                    .collect(Collectors.toList());
        }

        return Modelo.builder()
                .nombre(request.getNombreModelo())
                .precio(request.getPrecio())
                .costoActual(request.getCostoActual())
                .idMarca(request.getIdMarca())
                .idCategoria(request.getIdCategoria())
                .idEstilo(request.getIdEstilo())
                .idGenero(request.getIdGenero())
                .colores(colores)
                .build();
    }

    private ModeloColor toModeloColorDomain(RegistrarModeloRequest.ColorRequest request) {
        if (request == null) return null;

        List<ModeloColorFoto> fotos = buildFotosDomain(request);

        return ModeloColor.builder()
                .idColor(request.getIdColor())
                .codigo(request.getCodigo())
                .fotoUrl(request.getFotoUrl())
                .fotos(fotos)
                .build();
    }

    private List<ModeloColorFoto> buildFotosDomain(RegistrarModeloRequest.ColorRequest request) {
        LinkedHashSet<String> urls = new LinkedHashSet<>();

        if (request.getFotoUrl() != null && !request.getFotoUrl().isBlank()) {
            urls.add(request.getFotoUrl());
        }

        if (request.getFotos() != null) {
            request.getFotos().stream()
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(url -> !url.isBlank())
                    .forEach(urls::add);
        }

        List<ModeloColorFoto> fotos = new ArrayList<>();
        int orden = 1;
        for (String url : urls) {
            fotos.add(ModeloColorFoto.builder()
                    .fotoUrl(url)
                    .orden(orden)
                    .esPrincipal(orden == 1)
                    .build());
            orden++;
        }
        return fotos;
    }

    public ModeloListadoDTO toListadoDTO(Modelo modelo) {
        if (modelo == null) return null;

        List<ModeloListadoDTO.ModeloColorListadoDTO> coloresDTO = new ArrayList<>();
        if (modelo.getColores() != null) {
            coloresDTO = modelo.getColores().stream()
                    .map(this::toModeloColorListadoDTO)
                    .collect(Collectors.toList());
        }

        return ModeloListadoDTO.builder()
                .id(modelo.getId())
                .nombre(modelo.getNombre())
                .precio(modelo.getPrecio())
                .costoActual(modelo.getCostoActual())
                .marca(catalogoMapper.toMarcaDTO(modelo.getMarca()))
                .categoria(catalogoMapper.toCategoriaDTO(modelo.getCategoria()))
                .estilo(catalogoMapper.toEstiloDTO(modelo.getEstilo()))
                .colores(coloresDTO)
                .build();
    }

    private ModeloListadoDTO.ModeloColorListadoDTO toModeloColorListadoDTO(ModeloColor modeloColor) {
        if (modeloColor == null) return null;
        
        return ModeloListadoDTO.ModeloColorListadoDTO.builder()
                .id(modeloColor.getId())
                .codigo(modeloColor.getCodigo())
                .fotoUrl(modeloColor.getFotoUrl())
                .fotos(toFotoUrlList(modeloColor.getFotos()))
                .color(catalogoMapper.toColorDTO(modeloColor.getColor()))
                .build();
    }

    // Response mapping
    public ModeloDTO toDTO(Modelo modelo) {
        if (modelo == null) return null;

        List<ModeloDTO.ModeloColorDTO> coloresDTO = new ArrayList<>();
        if (modelo.getColores() != null) {
            coloresDTO = modelo.getColores().stream()
                    .map(this::toModeloColorDTO)
                    .collect(Collectors.toList());
        }

        return ModeloDTO.builder()
                .id(modelo.getId())
                .nombre(modelo.getNombre())
                .precio(modelo.getPrecio())
                .costoActual(modelo.getCostoActual())
                .marca(catalogoMapper.toMarcaDTO(modelo.getMarca()))
                .categoria(catalogoMapper.toCategoriaDTO(modelo.getCategoria()))
                .estilo(catalogoMapper.toEstiloDTO(modelo.getEstilo()))
                .genero(catalogoMapper.toGeneroDTO(modelo.getGenero()))
                .colores(coloresDTO)
                .build();
    }

    private ModeloDTO.ModeloColorDTO toModeloColorDTO(ModeloColor modeloColor) {
        if (modeloColor == null) return null;

        List<ModeloDTO.VarianteDTO> variantesDTO = new ArrayList<>();
        if (modeloColor.getVariantes() != null) {
            variantesDTO = modeloColor.getVariantes().stream()
                    .map(this::toVarianteDTO)
                    .collect(Collectors.toList());
        }

        return ModeloDTO.ModeloColorDTO.builder()
                .id(modeloColor.getId())
                .codigo(modeloColor.getCodigo())
                .fotoUrl(modeloColor.getFotoUrl())
                .fotos(toFotoUrlList(modeloColor.getFotos()))
                .color(catalogoMapper.toColorDTO(modeloColor.getColor()))
                .variantes(variantesDTO)
                .build();
    }

    private List<String> toFotoUrlList(List<ModeloColorFoto> fotos) {
        if (fotos == null) {
            return new ArrayList<>();
        }
        return fotos.stream()
                .filter(Objects::nonNull)
                .map(ModeloColorFoto::getFotoUrl)
                .filter(Objects::nonNull)
                .filter(url -> !url.isBlank())
                .collect(Collectors.toList());
    }

    private ModeloDTO.VarianteDTO toVarianteDTO(Variante variante) {
        if (variante == null) return null;
        return ModeloDTO.VarianteDTO.builder()
                .id(variante.getId())
                .talla(catalogoMapper.toTallaDTO(variante.getTalla()))
                .build();
    }
}
