package com.sistemasTarija.romeroChampions.venta.domain.exception;

public class VentaFailedException extends RuntimeException {
    public VentaFailedException(String message) {
        super(message);
    }
    public VentaFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
