package com.sistemasTarija.romeroChampions.catalogo.domain.model;
import com.sistemasTarija.romeroChampions.catalogo.domain.model.options.Color;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModeloColor {
    private Integer id;
    private String codigo;
    private String fotoUrl;
    private Integer idColor;
    private Color color;

    @Builder.Default
    private List<ModeloColorFoto> fotos = new ArrayList<>();
    
    @Builder.Default
    private List<Variante> variantes = new ArrayList<>();
}
