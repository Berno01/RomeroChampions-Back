package com.sistemasTarija.romeroChampions.inventario.domain.model.options;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estilo {
    private Integer id;
    private String nombre;
    private Boolean estado;
}
