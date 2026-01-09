package com.sistemasTarija.romeroChampions.catalogo.domain.exception.options;

public class TallaFailedException extends RuntimeException {
    public TallaFailedException(String message) {
        super(message);
    }
    public TallaFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
