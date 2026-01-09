package com.sistemasTarija.romeroChampions.auth.domain.exception;

public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
