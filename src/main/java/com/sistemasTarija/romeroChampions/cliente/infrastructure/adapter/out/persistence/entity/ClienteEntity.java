package com.sistemasTarija.romeroChampions.cliente.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Integer idCliente;
    
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    
    @Column(name = "celular")
    private String celular;
    
    @Column(name = "lugar_trabajo")
    private String lugarTrabajo;
    
    @Column(name = "direccion_casa")
    private String direccionCasa;
    
    @Column(name = "registrado_por")
    private Integer registradoPor;
    
    @Column(name = "estado")
    private Boolean estado;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
