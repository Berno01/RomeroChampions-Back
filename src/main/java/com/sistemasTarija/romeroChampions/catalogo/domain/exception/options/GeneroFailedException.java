package com.sistemasTarija.romeroChampions.catalogo.domain.exception.options;

public class GeneroFailedException extends RuntimeException {
    public GeneroFailedException(String message) {
        super(message);
    }
    public GeneroFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
