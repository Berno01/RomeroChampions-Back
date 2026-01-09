package com.sistemasTarija.romeroChampions.inventario.application.dto;

import lombok.*;

import java.util.Map;

/**
 * DTO para el detalle de cada sucursal en la matriz
 * Contiene los stocks agrupados por color y talla para una sucursal específica
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SucursalStockDTO {
    private Integer idSucursal;
    private String nombreSucursal;
    
    // Matriz: Map<NombreColor, Map<NombreTalla, VarianteStockDTO>>
    // Ejemplo: {"Azul": {"S": {idVariante:1, stock:5}, "M": {idVariante:2, stock:3}}}
    private Map<String, Map<String, VarianteStockDTO>> matrizColorTalla;
}
