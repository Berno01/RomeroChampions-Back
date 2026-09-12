package com.sistemasTarija.romeroChampions.catalogo.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.ColorDTO;
import com.sistemasTarija.romeroChampions.catalogo.application.dto.options.OptionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModeloListadoDTO {
    private Integer id;
    private String nombre;
    private Double precio;
    private OptionDTO marca;
    private OptionDTO categoria;
    private OptionDTO estilo;
    private OptionDTO genero;
    private List<ModeloColorListadoDTO> colores;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ModeloColorListadoDTO {
        private Integer id;
        private String codigo;
        private String fotoUrl;
        private ColorDTO color;
    }
}
