package com.sistemasTarija.romeroChampions.catalogo.application.dto;

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
public class CatalogoOptionsResponse {
    private List<OptionDTO> marcas;
    private List<OptionDTO> categorias;
    private List<OptionDTO> estilos;
    private List<OptionDTO> generos;
    private List<OptionDTO> tallas;
    private List<ColorDTO> colores;
}
