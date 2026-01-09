package com.sistemasTarija.romeroChampions.inventario.domain.exception;

public class InventarioNotFoundException extends RuntimeException {
    public InventarioNotFoundException(String message) {
        super(message);
    }
}
