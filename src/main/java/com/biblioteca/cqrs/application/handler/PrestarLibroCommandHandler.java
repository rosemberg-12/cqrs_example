package com.biblioteca.cqrs.application.handler;

import com.biblioteca.cqrs.application.command.PrestarLibroCommand;
import com.biblioteca.cqrs.domain.Libro;
import com.biblioteca.cqrs.infrastructure.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrestarLibroCommandHandler {
    private final LibroRepository libroRepository;

    public void handle(PrestarLibroCommand command) {
        log.info("[COMMAND] Ejecutando PrestarLibroCommand para libro {}", command.libroId());
        Libro libro = libroRepository.buscarPorId(command.libroId());
        libro.prestar(command.usuarioId());
        libroRepository.guardar(libro);
    }
}
