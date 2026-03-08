package com.biblioteca.cqrs.infrastructure.http;

import com.biblioteca.cqrs.domain.exception.LibroNoEncontradoException;
import com.biblioteca.cqrs.domain.exception.LibroYaRegistradoException;
import com.biblioteca.cqrs.domain.exception.OperacionLibroInvalidaException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LibroNoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleLibroNoEncontradoException(
        LibroNoEncontradoException ex, HttpServletRequest request
    ) {
        return buildErrorBody(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({LibroYaRegistradoException.class, OperacionLibroInvalidaException.class})
    public ResponseEntity<Map<String, Object>> handleConflicts(RuntimeException ex, HttpServletRequest request) {
        return buildErrorBody(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
        Exception ex, HttpServletRequest request
    ) {
        String message = "Solicitud invalida";
        if (ex instanceof MethodArgumentNotValidException methodArgumentNotValidException) {
            FieldError fieldError = methodArgumentNotValidException.getBindingResult().getFieldError();
            if (fieldError != null) {
                message = fieldError.getDefaultMessage();
            }
        }
        return buildErrorBody(HttpStatus.BAD_REQUEST, message, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex, HttpServletRequest request) {
        return buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del sistema", request.getRequestURI());
    }

    private ResponseEntity<Map<String, Object>> buildErrorBody(HttpStatus status, String message, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        return ResponseEntity.status(status).body(body);
    }
}
