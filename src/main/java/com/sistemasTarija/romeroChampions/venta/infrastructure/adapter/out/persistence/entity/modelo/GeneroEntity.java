package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "genero")
@Getter
@Setter
public class GeneroEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Integer id;

    @Column(name = "nombre_genero")
    private String nombre;
}
