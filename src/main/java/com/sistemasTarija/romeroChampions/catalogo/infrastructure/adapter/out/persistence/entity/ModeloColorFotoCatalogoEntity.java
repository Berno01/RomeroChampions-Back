package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modelo_color_foto")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ModeloColorFotoCatalogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo_color_foto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo_color", nullable = false)
    private ModeloColorCatalogoEntity modeloColor;

    @Column(name = "foto_url", nullable = false)
    private String fotoUrl;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "es_principal", nullable = false)
    @Builder.Default
    private Boolean esPrincipal = false;

    @Column(name = "estado", nullable = false)
    @Builder.Default
    private Boolean estado = true;
}
