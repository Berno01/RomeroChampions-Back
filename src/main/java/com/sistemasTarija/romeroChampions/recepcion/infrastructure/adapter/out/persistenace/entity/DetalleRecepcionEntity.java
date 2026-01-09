package com.sistemasTarija.romeroChampions.recepcion.infrastructure.adapter.out.persistenace.entity;

import com.sistemasTarija.romeroChampions.venta.infrastructure.adapter.out.persistence.entity.modelo.VarianteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "detalle_recepcion")
public class DetalleRecepcionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_recepcion")
    private Long idDetalleRecepcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_recepcion", nullable = false)
    private RecepcionEntity recepcion;

    @Column(name = "id_variante")
    private Integer idVariante;
    
    // Relación solo para lectura
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_variante", insertable = false, updatable = false)
    private VarianteEntity variante;
    
    @Column(name = "cantidad")
    private Integer cantidad;
}
