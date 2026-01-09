package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estilo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstiloCatalogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estilo")
    private Integer id;

    @Column(name = "nombre_estilo")
    private String nombre;

    @Column(name = "estado")
    @Builder.Default
    private Boolean estado = true;
}
