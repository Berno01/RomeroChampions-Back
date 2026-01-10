package com.sistemasTarija.romeroChampions.credito.application.port.out;

import com.sistemasTarija.romeroChampions.credito.domain.model.PagoCredito;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PagoCreditoPersistencePort {
    PagoCredito save(PagoCredito pago);
    Optional<PagoCredito> findById(Integer idPago);
    List<PagoCredito> findByVentaId(Integer idVenta);
    List<PagoCredito> findAllByFilters(Integer idSucursal, LocalDateTime fechaInicio, LocalDateTime fechaFin);
    void delete(Integer idPago);
}
