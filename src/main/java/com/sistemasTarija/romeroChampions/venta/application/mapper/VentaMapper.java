package com.sistemasTarija.romeroChampions.venta.application.mapper;

import com.sistemasTarija.romeroChampions.venta.application.dto.DetalleVentaDTO;
import com.sistemasTarija.romeroChampions.venta.application.dto.VentaDTO;
import com.sistemasTarija.romeroChampions.venta.domain.model.DetalleVenta;
import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {
    public Venta toDomain(VentaDTO dto) {
        List<DetalleVenta> detalles = dto.getDetalleVenta().stream()
                .map(this::toDetalleVentaDomain)
                .collect(Collectors.toList());

        return new Venta(
                dto.getIdVenta(),
                LocalDateTime.now(), // Se genera automáticamente la fecha
                dto.getIdSucursal(),
                dto.getIdCliente(),
                dto.getMontoEfectivo(),
                dto.getMontoQr(),
                dto.getMontoTarjeta(),
                dto.getDescuento(),
                dto.getTipoDescuento(),
                dto.getTipo(),
                dto.getFechaLimite(),
                detalles
        );
    }

    private DetalleVenta toDetalleVentaDomain(DetalleVentaDTO dto) {
        DetalleVenta detalle = new DetalleVenta(
                dto.getIdVariante(),
                dto.getCantidad(),
                dto.getPrecioUnitario()
        );
        detalle.setCostoUnitario(dto.getCostoUnitario());
        detalle.setGananciaUnitaria(dto.getGananciaUnitaria());
        detalle.setGananciaTotal(dto.getGananciaTotal());
        return detalle;
    }

    public VentaDTO toDto(Venta domain) {
        // Convertimos los detalles
        List<DetalleVentaDTO> detallesDto = domain.getDetalleVenta().stream()
                .map(this::toDetalleVentaDto)
                .collect(Collectors.toList());

        return new VentaDTO(
                domain.getIdVenta(),
                domain.getFecha(),
                domain.getIdSucursal(),
                domain.getTotal(),
                domain.getMontoEfectivo(),
                domain.getMontoQr(),
                domain.getMontoTarjeta(),
                domain.getSaldoPendiente(),
                domain.getFechaLimite(),
                domain.getDescuento(),
                domain.getTipoDescuento(),
                domain.getTipoVenta(),
                domain.getIdCliente(),
                domain.getCreatedBy(),
                null, // username - se setea en el servicio
                detallesDto
        );
    }

    public VentaDTO toDtoWithoutDetails(Venta domain) {
        return new VentaDTO(
                domain.getIdVenta(),
                domain.getFecha(),
                domain.getIdSucursal(),
                domain.getTotal(),
                domain.getMontoEfectivo(),
                domain.getMontoQr(),
                domain.getMontoTarjeta(),
                domain.getSaldoPendiente(),
                domain.getFechaLimite(),
                domain.getDescuento(),
                domain.getTipoDescuento(),
                domain.getTipoVenta(),
                domain.getIdCliente(),
                domain.getCreatedBy(),
                null, // username - se setea en el servicio
                null // Sin detalle de venta para listados
        );
    }

    private DetalleVentaDTO toDetalleVentaDto(DetalleVenta domain) {
        DetalleVentaDTO dto = new DetalleVentaDTO(
                domain.getIdVariante(),
                domain.getCantidad(),
                domain.getPrecioUnitario(),
                domain.getTotal()
        );
        dto.setCostoUnitario(domain.getCostoUnitario());
        dto.setGananciaUnitaria(domain.getGananciaUnitaria());
        dto.setGananciaTotal(domain.getGananciaTotal());
        // Agregar idModelo para respuesta al frontend
        dto.setIdModelo(domain.getIdModelo());
        return dto;
    }

    // Helper para listas
    public List<VentaDTO> toDtoList(List<Venta> domainList) {
        return domainList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

}
