package com.sistemasTarija.romeroChampions.venta.application.dto.catalogo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenPrendaDTO {
    private Integer idModelo;
    private String nombreModelo;
    private Double precio;
    private String nombreMarca;
    private String nombreCategoria;
    private String fotoPortada; // Una sola foto representativa
    private Long stockTotal; // La suma total (Long porque SQL SUM devuelve Long)
    private Boolean pocasUnidades; // Calculado: true si stock < 5
    private List<String> codigos; // Array de códigos únicos de cada color del modelo
    private List<String> tallas;  // Array de tallas únicas disponibles
    
    // Constructor usado por la query JPQL (sin códigos ni tallas)
    public ResumenPrendaDTO(Integer idModelo, String nombreModelo, Double precio, 
                           String nombreMarca, String nombreCategoria, String fotoPortada, 
                           Long stockTotal, Boolean pocasUnidades) {
        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
        this.precio = precio;
        this.nombreMarca = nombreMarca;
        this.nombreCategoria = nombreCategoria;
        this.fotoPortada = fotoPortada;
        this.stockTotal = stockTotal;
        this.pocasUnidades = pocasUnidades;
    }
}
