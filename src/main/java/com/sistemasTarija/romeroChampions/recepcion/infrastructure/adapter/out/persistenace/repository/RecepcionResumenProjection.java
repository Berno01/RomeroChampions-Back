package com.sistemasTarija.romeroChampions.recepcion.infrastructure.adapter.out.persistenace.repository;

import java.time.LocalDateTime;

public interface RecepcionResumenProjection {
    Integer getIdRecepcion();
    LocalDateTime getFecha();
    Integer getIdSucursal();
    Boolean getEstado();
    Long getTotalItems(); // Ya no se usará directamente para suma total, sino cantidad parcial
    Integer getCantidad(); // Cantidad de la línea
    String getNombreMarca();
    String getNombreCategoria();
}
