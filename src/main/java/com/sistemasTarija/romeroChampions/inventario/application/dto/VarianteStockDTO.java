package com.sistemasTarija.romeroChampions.inventario.application.dto;

import lombok.*;

/**
 * DTO interno para cada celda de la matriz Color x Talla
 * Representa el stock de una variante específica en una sucursal
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VarianteStockDTO {
    private Integer idVariante;
    private String nombreTalla;
    private String nombreColor;
    private String codigoHexColor;
    private String codigoModeloColor;  // Código único del modelo-color
    private Integer stock;  // Stock en la sucursal específica (0 si no existe registro)
}
