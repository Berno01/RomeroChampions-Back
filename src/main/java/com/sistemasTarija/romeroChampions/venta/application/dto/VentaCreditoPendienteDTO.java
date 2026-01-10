package com.sistemasTarija.romeroChampions.venta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VentaCreditoPendienteDTO {
    private Integer idVenta;
    private LocalDateTime fechaVenta;
    private LocalDateTime fechaLimite;
    private Double saldoPendiente;
    private Integer cantidadItems;
    private Double totalOriginal;
}
