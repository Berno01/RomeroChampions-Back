package com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.in.web.controller;

import com.sistemasTarija.romeroChampions.credito.application.dto.PagoCreditoDTO;
import com.sistemasTarija.romeroChampions.credito.application.dto.PagoFilterDTO;
import com.sistemasTarija.romeroChampions.credito.application.port.in.ManagePagoUseCase;
import com.sistemasTarija.romeroChampions.venta.application.dto.ResumenDeudaClienteDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaCreditoPendienteDTO;
import com.sistemasTarija.romeroChampions.venta.application.port.in.GestionDeudasUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/credito")
@RequiredArgsConstructor
public class PagoCreditoController {

    private final ManagePagoUseCase managePagoUseCase;
    private final GestionDeudasUseCase gestionDeudasUseCase;

    @PostMapping
    public ResponseEntity<PagoCreditoDTO> createPayment(@RequestBody PagoCreditoDTO dto) {
        return ResponseEntity.ok(managePagoUseCase.createPayment(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoCreditoDTO> updatePayment(
            @PathVariable Integer id,
            @RequestBody PagoCreditoDTO dto) {
        return ResponseEntity.ok(managePagoUseCase.updatePayment(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(
            @PathVariable Integer id,
            @RequestParam Integer idUsuario) {
        managePagoUseCase.deletePayment(id, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/venta/{idVenta}")
    public ResponseEntity<List<PagoCreditoDTO>> getPaymentsByVenta(@PathVariable Integer idVenta) {
        return ResponseEntity.ok(managePagoUseCase.getPaymentsByVenta(idVenta));
    }

    // Listado de Pagos con Filtros (GET)
    @GetMapping
    public ResponseEntity<List<PagoCreditoDTO>> getAllPayments(
            @RequestParam Integer idUsuario,
            @RequestParam(required = false) Integer idSucursal,
            @RequestParam(value = "fecha", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(value = "fecha_fin", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin
    ) {
        PagoFilterDTO filtro = new PagoFilterDTO(idSucursal, fecha, fechaFin);
        return ResponseEntity.ok(managePagoUseCase.getPaymentsByFilter(filtro, idUsuario));
    }

    // --- Endpoints de Deudas y Cobranza ---

    @GetMapping("/deudores")
    public ResponseEntity<List<ResumenDeudaClienteDTO>> getResumenDeudores(@RequestHeader("X-Usuario-Id") Integer idUsuario) {
        return ResponseEntity.ok(gestionDeudasUseCase.getResumenDeudores(idUsuario));
    }

    @GetMapping("/deudas/cliente/{idCliente}")
    public ResponseEntity<List<VentaCreditoPendienteDTO>> getVentasPendientesPorCliente(
            @PathVariable Integer idCliente,
            @RequestHeader("X-Usuario-Id") Integer idUsuario) {
        return ResponseEntity.ok(gestionDeudasUseCase.getVentasPendientesPorCliente(idCliente, idUsuario));
    }
}
