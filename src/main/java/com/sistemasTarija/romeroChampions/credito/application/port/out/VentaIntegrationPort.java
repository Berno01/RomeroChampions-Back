package com.sistemasTarija.romeroChampions.credito.application.port.out;

import com.sistemasTarija.romeroChampions.venta.domain.model.Venta;
import java.util.Optional;

public interface VentaIntegrationPort {
    Optional<Venta> findVentaById(Integer idVenta);
    void updateVenta(Venta venta);
}
