package com.biblioteca.cqrs.infrastructure.http;

import com.biblioteca.cqrs.application.command.DevolverLibroCommand;
import com.biblioteca.cqrs.application.command.PrestarLibroCommand;
import com.biblioteca.cqrs.application.command.RegistrarLibroCommand;
import com.biblioteca.cqrs.application.handler.DevolverLibroCommandHandler;
import com.biblioteca.cqrs.application.handler.PrestarLibroCommandHandler;
import com.biblioteca.cqrs.application.handler.RegistrarLibroCommandHandler;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/libros")
public class CommandController {
    private final RegistrarLibroCommandHandler registrarLibroCommandHandler;
    private final PrestarLibroCommandHandler prestarLibroCommandHandler;
    private final DevolverLibroCommandHandler devolverLibroCommandHandler;

    public CommandController(
        RegistrarLibroCommandHandler registrarLibroCommandHandler,
        PrestarLibroCommandHandler prestarLibroCommandHandler,
        DevolverLibroCommandHandler devolverLibroCommandHandler
    ) {
        this.registrarLibroCommandHandler = registrarLibroCommandHandler;
        this.prestarLibroCommandHandler = prestarLibroCommandHandler;
        this.devolverLibroCommandHandler = devolverLibroCommandHandler;
    }

    @PostMapping
    public ResponseEntity<Void> registrarLibro(@Valid @RequestBody RegistrarLibroCommand command) {
        registrarLibroCommandHandler.handle(command);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/prestar")
    public ResponseEntity<Void> prestarLibro(@Valid @RequestBody PrestarLibroCommand command) {
        prestarLibroCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/devolver")
    public ResponseEntity<Void> devolverLibro(@Valid @RequestBody DevolverLibroCommand command) {
        devolverLibroCommandHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}
