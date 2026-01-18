package com.sistemasTarija.romeroChampions.dashboard.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO para mostrar stock total por categoría
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockPorCategoriaDTO {
    private Integer idCategoria;
    private String nombreCategoria;
    private Long stockTotal;  // SUM retorna Long
}
