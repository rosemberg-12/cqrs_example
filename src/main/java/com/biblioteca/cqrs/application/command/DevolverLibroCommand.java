package com.biblioteca.cqrs.application.command;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;

public record DevolverLibroCommand(@NotBlank(message = "El id del libro es obligatorio") String libroId) {
}
