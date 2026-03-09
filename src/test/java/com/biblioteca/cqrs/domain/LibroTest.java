package com.biblioteca.cqrs.domain;

import com.biblioteca.cqrs.domain.exception.OperacionLibroInvalidaException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LibroTest {

    @Test
    void deberiaPrestarYDevolverLibro() {
        Libro libro = new Libro("1", "CQRS");

        libro.prestar("u-1");
        assertFalse(libro.estaDisponible());
        assertEquals("u-1", libro.getUsuarioId());

        libro.devolver();
        assertTrue(libro.estaDisponible());
        assertNull(libro.getUsuarioId());
    }

    @Test
    void noDeberiaPermitirPrestarLibroDosVeces() {
        Libro libro = new Libro("1", "CQRS");
        libro.prestar("u-1");

        assertThrows(OperacionLibroInvalidaException.class, () -> libro.prestar("u-2"));
    }
}
