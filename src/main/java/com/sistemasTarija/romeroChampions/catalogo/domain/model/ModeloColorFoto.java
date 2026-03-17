package com.sistemasTarija.romeroChampions.catalogo.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModeloColorFoto {
    private Long id;
    private String fotoUrl;
    private Integer orden;
    private Boolean esPrincipal;
}
