package com.sistemasTarija.romeroChampions.catalogo.infrastructure.adapter.out.persistence.entity.options;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genero")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneroCatalogoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Integer id;

    @Column(name = "nombre_genero")
    private String nombre;

    @Column(name = "estado")
    @Builder.Default
    private Boolean estado = true;
}
