package com.sistemasTarija.romeroChampions.catalogo.domain.exception.options;

public class EstiloFailedException extends RuntimeException {
    public EstiloFailedException(String message) {
        super(message);
    }
    public EstiloFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
