package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.application.command.DevolverLibroCommand;
import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DevolverLibroCommandHandler {
    private final LibroRepository libroRepository;

    public void handle(DevolverLibroCommand command) {
        log.info("[COMMAND] Ejecutando DevolverLibroCommand para libro {}", command.getLibroId());
        Libro libro = libroRepository.buscarPorId(command.getLibroId());
        libro.devolver();
        libroRepository.guardar(libro);
    }
}
