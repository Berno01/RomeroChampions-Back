package com.sistemasTarija.romeroChampions.credito.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoFilterDTO {
    private Integer idSucursal;
    private LocalDate fecha;
    private LocalDate fechaFin;
}
