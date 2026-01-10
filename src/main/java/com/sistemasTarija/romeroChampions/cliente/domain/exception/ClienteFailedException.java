package com.sistemasTarija.romeroChampions.cliente.domain.exception;

public class ClienteFailedException extends RuntimeException {
    public ClienteFailedException(String message) {
        super(message);
    }

    public ClienteFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
