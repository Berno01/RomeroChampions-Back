package com.sistemasTarija.romeroChampions.inventario.domain.model;

import com.sistemasTarija.romeroChampions.inventario.domain.model.options.Talla;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Variante {
    private Integer id;
    private Integer idTalla;
    private Talla talla;
}
