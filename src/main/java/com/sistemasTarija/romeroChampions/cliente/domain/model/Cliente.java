package com.sistemasTarija.romeroChampions.cliente.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cliente {
    private Integer idCliente;
    private String nombreCompleto;
    private String celular;
    private String lugarTrabajo;
    private String direccionCasa;
    private Integer registradoPor;
    private Boolean estado;
    private LocalDateTime createdAt;
}
