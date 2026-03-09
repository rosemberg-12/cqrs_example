package com.biblioteca.cqrs.domain.exception;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(String libroId) {
        super("No existe un libro con id: " + libroId);
    }
}
