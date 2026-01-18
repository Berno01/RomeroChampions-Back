package com.sistemasTarija.romeroChampions.inventario.application.dto;

import lombok.*;

import java.util.List;

/**
 * DTO para el listado general de inventario (Endpoint 1)
 * Representa el resumen de cada modelo con su stock total global
 */
@Getter
@Setter
@NoArgsConstructor
@Builder
public class InventarioResumenDTO {
    private Integer idModelo;
    private String nombreModelo;
    private String fotoPortada;
    private String categoria;
    private String marca;
    private String estilo;
    private Long totalStockGlobal;  // SUM(i.cantidad) de todas las variantes en todas las sucursales
    private List<String> codigos;    // Array de códigos únicos de cada color del modelo

    // Constructor para JPQL (sin codigos - se popula después)
    public InventarioResumenDTO(Integer idModelo, String nombreModelo, String fotoPortada,
                                 String categoria, String marca, String estilo, Long totalStockGlobal) {
        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
        this.fotoPortada = fotoPortada;
        this.categoria = categoria;
        this.marca = marca;
        this.estilo = estilo;
        this.totalStockGlobal = totalStockGlobal;
    }

    // Constructor completo (para tests y uso manual)
    public InventarioResumenDTO(Integer idModelo, String nombreModelo, String fotoPortada,
                                 String categoria, String marca, String estilo, Long totalStockGlobal,
                                 List<String> codigos) {
        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
        this.fotoPortada = fotoPortada;
        this.categoria = categoria;
        this.marca = marca;
        this.estilo = estilo;
        this.totalStockGlobal = totalStockGlobal;
        this.codigos = codigos;
    }
}
