package com.sistemasTarija.romeroChampions.catalogo.domain.exception.options;

public class MarcaFailedException extends RuntimeException {
    public MarcaFailedException(String message) {
        super(message);
    }
    public MarcaFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
