package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "estilo")
@Getter
@Setter
public class EstiloEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estilo")
    private Integer id;

    @Column(name = "nombre_estilo")
    private String nombre;
}
