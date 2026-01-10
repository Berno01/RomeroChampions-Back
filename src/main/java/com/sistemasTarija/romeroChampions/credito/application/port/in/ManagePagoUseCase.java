package com.sistemasTarija.romeroChampions.credito.application.port.in;

import com.sistemasTarija.romeroChampions.credito.application.dto.PagoCreditoDTO;
import com.sistemasTarija.romeroChampions.credito.application.dto.PagoFilterDTO;
import java.util.List;

public interface ManagePagoUseCase {
    PagoCreditoDTO createPayment(PagoCreditoDTO dto);
    PagoCreditoDTO updatePayment(Integer idPago, PagoCreditoDTO dto);
    void deletePayment(Integer idPago, Integer idUsuario);
    List<PagoCreditoDTO> getPaymentsByVenta(Integer idVenta);
    List<PagoCreditoDTO> getPaymentsByFilter(PagoFilterDTO filter, Integer idUsuario);
}
