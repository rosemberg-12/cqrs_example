package com.biblioteca.cqrs.application.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

public record PrestarLibroCommand(@NotBlank(message = "El id del libro es obligatorio") String libroId,
                                  @NotBlank(message = "El id del usuario es obligatorio") String usuarioId) {
}
