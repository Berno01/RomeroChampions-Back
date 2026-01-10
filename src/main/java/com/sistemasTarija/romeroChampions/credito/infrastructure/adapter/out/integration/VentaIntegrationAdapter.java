package com.sistemasTarija.romeroChampions.credito.infrastructure.adapter.out.integration;

import com.sistemasTarija.romeroChampions.credito.application.port.out.VentaIntegrationPort;
import com.sistemasTarija.romeroChampions.venta.application.port.out.VentaPersistancePort;
import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class VentaIntegrationAdapter implements VentaIntegrationPort {

    private final VentaPersistancePort ventaPersistancePort;

    @Override
    public Optional<Venta> findVentaById(Integer idVenta) {
        // Asumiendo sucursal null para búsqueda global o admin, o podemos refinar luego
        return ventaPersistancePort.findByIdAndSucursal(idVenta, null);
    }

    @Override
    public void updateVenta(Venta venta) {
        ventaPersistancePort.save(venta);
    }
}
