package com.biblioteca.cqrs.domain.exception;

public class LibroYaRegistradoException extends RuntimeException {
    public LibroYaRegistradoException(String libroId) {
        super("Ya existe un libro con id: " + libroId);
    }
}
