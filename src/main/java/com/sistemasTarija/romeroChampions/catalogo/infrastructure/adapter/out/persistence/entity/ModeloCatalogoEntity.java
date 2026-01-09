package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity;

import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.CategoriaCatalogoEntity;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.EstiloCatalogoEntity;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.GeneroCatalogoEntity;
import com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options.MarcaCatalogoEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modelo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModeloCatalogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Integer id;

    @Column(name = "nombre_modelo")
    private String nombre;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "id_marca")
    private Integer idMarca;

    @ManyToOne
    @JoinColumn(name = "id_marca", insertable = false, updatable = false)
    private MarcaCatalogoEntity marca;

    @Column(name = "id_categoria")
    private Integer idCategoria;

    @ManyToOne
    @JoinColumn(name = "id_categoria", insertable = false, updatable = false)
    private CategoriaCatalogoEntity categoria;

    @Column(name = "id_estilo")
    private Integer idEstilo;

    @ManyToOne
    @JoinColumn(name = "id_estilo", insertable = false, updatable = false)
    private EstiloCatalogoEntity estilo;

    @Column(name = "id_genero")
    private Integer idGenero;

    @ManyToOne
    @JoinColumn(name = "id_genero", insertable = false, updatable = false)
    private GeneroCatalogoEntity genero;

    @Column(name = "estado")
    @Builder.Default
    private Boolean estado = true;

    @OneToMany(mappedBy = "modelo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ModeloColorCatalogoEntity> colores = new ArrayList<>();
}
