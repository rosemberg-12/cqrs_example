package com.biblioteca.cqrs.application.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegistrarLibroCommand {
    @NotBlank(message = "El id del libro es obligatorio")
    private final String id;
    @NotBlank(message = "El titulo del libro es obligatorio")
    private final String titulo;
}
