package com.biblioteca.cqrs.application.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PrestarLibroCommand {
    @NotBlank(message = "El id del libro es obligatorio")
    private final String libroId;
    @NotBlank(message = "El id del usuario es obligatorio")
    private final String usuarioId;
}
