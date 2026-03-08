package com.biblioteca.cqrs.domain.exception;

public class OperacionLibroInvalidaException extends RuntimeException {
    public OperacionLibroInvalidaException(String message) {
        super(message);
    }
}
