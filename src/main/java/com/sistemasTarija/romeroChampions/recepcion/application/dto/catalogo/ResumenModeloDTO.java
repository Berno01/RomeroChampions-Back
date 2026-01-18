package com.sistemasTarija.romeroChampions.recepcion.application.dto.catalogo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResumenModeloDTO {
    @JsonProperty("idModelo")
    private Integer idModelo;
    
    @JsonProperty("nombreModelo")
    private String nombreModelo;
    
    @JsonProperty("nombreMarca")
    private String nombreMarca;
    
    @JsonProperty("nombreCategoria")
    private String nombreCategoria;
    
    @JsonProperty("fotoPortada")
    private String fotoPortada; // Una sola foto representativa
    
    @JsonProperty("codigos")
    private List<String> codigos; // Array de códigos únicos de cada color del modelo
    
    // Constructor usado por la query JPQL (sin códigos)
    public ResumenModeloDTO(Integer idModelo, String nombreModelo, String nombreMarca, 
                           String nombreCategoria, String fotoPortada) {
        this.idModelo = idModelo;
        this.nombreModelo = nombreModelo;
        this.nombreMarca = nombreMarca;
        this.nombreCategoria = nombreCategoria;
        this.fotoPortada = fotoPortada;
    }
}
