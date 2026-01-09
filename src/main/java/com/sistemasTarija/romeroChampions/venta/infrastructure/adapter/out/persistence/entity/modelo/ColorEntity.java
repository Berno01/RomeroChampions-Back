package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "color")
@Getter
@Setter
public class ColorEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_color")
    private Integer id;

    @Column(name = "nombre_color")
    private String nombre;

    @Column(name = "codigo_hex_color")
    private String codigoHex;
}
