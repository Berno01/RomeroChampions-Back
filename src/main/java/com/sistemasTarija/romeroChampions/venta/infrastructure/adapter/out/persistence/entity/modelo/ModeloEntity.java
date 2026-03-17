package com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.modelo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity @Table(name = "modelo") @Getter @Setter
public class ModeloEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Integer id;

    @Column(name = "nombre_modelo")
    private String nombre;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "costo_actual")
    private Double costoActual;

    @ManyToOne @JoinColumn(name = "id_marca")
    private MarcaEntity marca;

    @ManyToOne @JoinColumn(name = "id_categoria")
    private CategoriaEntity categoria;

    @ManyToOne @JoinColumn(name = "id_estilo")
    private EstiloEntity estilo;

    @ManyToOne @JoinColumn(name = "id_genero")
    private GeneroEntity genero;

    @Column(name = "estado")
    private Boolean estado;
}
