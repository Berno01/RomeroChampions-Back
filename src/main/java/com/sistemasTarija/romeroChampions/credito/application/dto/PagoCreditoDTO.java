package com.sistemasTarija.romeroChampions.credito.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PagoCreditoDTO {
    @JsonProperty("id_pago")
    private Integer idPago;
    @JsonProperty("id_venta")
    private Integer idVenta;
    @JsonProperty("monto_pago")
    private Double montoPago;
    @JsonProperty("fecha_pago")
    private LocalDateTime fechaPago;
    @JsonProperty("metodo_pago")
    private String metodoPago;
    @JsonProperty("observacion")
    private String observacion;
    @JsonProperty("estado")
    private Boolean estado;
    @JsonProperty("id_usuario")
    private Integer idUsuario; 
    
    // Campos informativos
    @JsonProperty("nombre_cliente")
    private String nombreCliente;
    @JsonProperty("nombre_sucursal")
    private String nombreSucursal;
    @JsonProperty("saldo_actual_venta")
    private Double saldoVentaActual;
}
