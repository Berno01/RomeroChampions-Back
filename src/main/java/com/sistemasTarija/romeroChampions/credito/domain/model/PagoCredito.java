package com.sistemasTarija.romeroChampions.credito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagoCredito {
    private Integer idPago;
    private Integer idVenta;
    private Double montoPago;
    private LocalDateTime fechaPago;
    private String metodoPago;
    private String observacion;
    private Boolean estado;
    private Integer createdBy;
    private LocalDateTime createdAt;
    private Integer updatedBy;
    private LocalDateTime updatedAt;

    // Campos extendidos para visualización
    private String nombreCliente;
    private String nombreSucursal;
    private Double saldoVentaActual;

    public boolean isValid(Double saldoPendienteVenta) {
        return montoPago != null && montoPago > 0 && montoPago <= saldoPendienteVenta;
    }
}
