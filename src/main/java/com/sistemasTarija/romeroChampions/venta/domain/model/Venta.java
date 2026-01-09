package com.sistemasTarija.romeroChampions.venta.domain.model;

import com.sistemasTarija.romeroChampions.venta.domain.exception.VentaFailedException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Venta {
    private Integer idVenta;
    private LocalDateTime fecha;
    private Integer idSucursal;
    private Double total;
    private Double montoEfectivo;
    private Double montoQr;
    private Double montoTarjeta;
    private Double saldoPendiente;
    private LocalDateTime fechaLimite;
    private Double descuento;
    private String tipoDescuento;
    private String tipoVenta;
    private Boolean estadoVenta;
    private LocalDateTime createdAt;
    private Integer createdBy;
    private LocalDateTime updatedAt;
    private Integer updatedBy;
    private List<DetalleVenta> detalleVenta;

    public Venta(Integer idVenta, LocalDateTime fecha, Integer idSucursal, Double montoEfectivo,  Double montoQr, Double montoTarjeta, Double descuento, String tipoDescuento, String tipoVenta, LocalDateTime fechaLimite, List<DetalleVenta> detalleVenta) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.idSucursal = idSucursal;
        this.detalleVenta = detalleVenta;
        this.descuento = descuento != null ? descuento : 0.0;
        calcularTotal();
        this.montoEfectivo = montoEfectivo != null ? montoEfectivo : 0.0;
        this.montoQr = montoQr != null ? montoQr : 0.0;
        this.montoTarjeta = montoTarjeta != null ? montoTarjeta : 0.0;
        this.tipoDescuento = tipoDescuento;
        this.tipoVenta = tipoVenta;
        this.fechaLimite = fechaLimite;
        this.estadoVenta = true;
        this.checkMontosPago();
    }


    public void checkMontosPago(){
        // Calcular Valor Real de la Venta (Precios Items - Descuento)
        double valorItems = this.detalleVenta.stream()
                .mapToDouble(DetalleVenta::getTotal)
                .sum();
        
        // Verificar que el descuento no sea igual o mayor al subtotal
        if (this.descuento >= valorItems) {
            throw new VentaFailedException("Error: El descuento (" + this.descuento + ") no puede ser igual o mayor al subtotal (" + valorItems + ")");
        }

        Double valorRealVenta = valorItems - this.descuento;
        
        // Suma de pagos iniciales
        Double sumaPagos = this.montoEfectivo + this.montoQr + this.montoTarjeta;
        
        if ("CONTADO".equalsIgnoreCase(this.tipoVenta)) {
            // En Contado: Total = Valor Real.
            this.total = valorRealVenta;
            
            // Validacion: Pago completo
            // Tolerancia pequeña para flotantes
            if (Math.abs(sumaPagos - this.total) > 0.1) { 
                throw new VentaFailedException("Error: Venta al CONTADO requiere pago completo (" + this.total + "). Pagado: " + sumaPagos);
            }
            this.saldoPendiente = 0.0;
            
        } else if ("CREDITO".equalsIgnoreCase(this.tipoVenta)) {
            // En Credito: Total = Suma de lo Pagado Ahora (Instrucción explícita)
            this.total = sumaPagos;
            
            // Validacion 1: Pago Inicial > 0
            if (this.total <= 0) {
                 throw new VentaFailedException("Error: Venta a CREDITO requiere un pago inicial mayor a 0.");
            }
            
            // Validacion 2: Pago Inicial < Valor Real
            if (this.total >= valorRealVenta) {
                throw new VentaFailedException("Error: El pago inicial ("+this.total+") cubre el total ("+valorRealVenta+"). Use venta al CONTADO.");
            }
            
            // Saldo Pendiente = Lo que falta pagar
            this.saldoPendiente = valorRealVenta - this.total;
            
            if (this.fechaLimite == null) {
                throw new VentaFailedException("Error: Venta a CREDITO requiere una fecha límite de pago.");
            }

        } else {
             throw new VentaFailedException("Error: Tipo de venta no válido (Use CONTADO o CREDITO).");
        }
    }

    public void actualizarDatos(List<DetalleVenta> nuevosDetalles, Double efectivo, Double qr, Double tarjeta, Double descuento, String tipoDescuento, String tipo, LocalDateTime fechaLimite) {
        this.detalleVenta = nuevosDetalles;
        this.montoEfectivo = efectivo != null ? efectivo : 0.0;
        this.montoQr = qr != null ? qr : 0.0;
        this.montoTarjeta = tarjeta != null ? tarjeta : 0.0;
        this.descuento = descuento != null ? descuento : 0.0;
        this.tipoDescuento = tipoDescuento;
        this.tipoVenta = tipo;
        this.fechaLimite = fechaLimite;
        this.calcularTotal();
        this.checkMontosPago();
    }

    private void calcularTotal() {
        // Obsoleto por checkMontosPago, pero se mantiene para lógica interna
        double subtotal = this.detalleVenta.stream()
                .mapToDouble(DetalleVenta::getTotal)
                .sum();
       // No asignamos this.total aqui, dejamos que checkMontosPago decida
    }
}
