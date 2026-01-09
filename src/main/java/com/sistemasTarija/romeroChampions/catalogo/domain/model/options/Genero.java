package com.sistemasTarija.romeroChampions.catalogo.domain.model.options;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Genero {
    private Integer id;
    private String nombre;
    private Boolean estado;
}
