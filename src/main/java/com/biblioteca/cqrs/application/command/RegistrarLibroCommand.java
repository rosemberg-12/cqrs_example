package com.biblioteca.cqrs.application.command;

import jakarta.validation.constraints.NotBlank;

public record RegistrarLibroCommand(@NotBlank(message = "El id del libro es obligatorio") String id,
                                    @NotBlank(message = "El titulo del libro es obligatorio") String titulo) {
}
