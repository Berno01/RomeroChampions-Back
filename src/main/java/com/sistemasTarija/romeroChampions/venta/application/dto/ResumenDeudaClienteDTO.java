package com.sistemasTarija.romeroChampions.venta.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumenDeudaClienteDTO {
    private Integer idCliente;
    private String nombreCompleto; // Se llenará en el servicio
    private String ci;             // Se llenará en el servicio
    private Long cantidadVentasPendientes;
    private Double totalDeuda;
    private LocalDateTime proximoVencimiento;
}
