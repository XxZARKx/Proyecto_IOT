package com.smartinventory.common.exception;

import com.smartinventory.common.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> notFound(ResourceNotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage(), Map.of());
    }

    @ExceptionHandler({BadRequestException.class, BadCredentialsException.class})
    ResponseEntity<ErrorResponse> badRequest(RuntimeException ex) {
        return error(HttpStatus.BAD_REQUEST, ex.getMessage(), Map.of());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponse> validation(MethodArgumentNotValidException ex) {
        Map<String, String> details = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (a, b) -> a));
        return error(HttpStatus.BAD_REQUEST, "Validacion fallida", details);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ErrorResponse> generic(Exception ex) {
        return error(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), Map.of());
    }

    private ResponseEntity<ErrorResponse> error(HttpStatus status, String message, Map<String, String> details) {
        return ResponseEntity.status(status).body(new ErrorResponse(LocalDateTime.now(), status.value(), message, details));
    }
}
