package com.sistemasTarija.romeroChampions.cliente.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    @JsonProperty("id_cliente")
    private Integer idCliente;
    
    @JsonProperty("nombre_completo")
    private String nombreCompleto;
    
    @JsonProperty("celular")
    private String celular;
    
    @JsonProperty("lugar_trabajo")
    private String lugarTrabajo;
    
    @JsonProperty("direccion_casa")
    private String direccionCasa;
    
    @JsonProperty("registrado_por")
    private Integer registradoPor;
    
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
