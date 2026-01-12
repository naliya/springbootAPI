package com.learning.springapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1) Validation errors from @Valid (e.g., @NotBlank, @Email, @NotNull)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors);
    }

    // 2) Bad JSON / wrong types (e.g., "age": "" when age is Integer)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleBadJson(HttpMessageNotReadableException ex) {
        // Keep message simple and safe for clients
        Map<String, Object> details = Map.of(
                "error", "Invalid request body. Check JSON format and field types (e.g., age must be a number)."
        );

        return buildError(HttpStatus.BAD_REQUEST, "Malformed JSON or wrong field type", details);
    }

    // 3) Your thrown ResponseStatusException (e.g., NOT_FOUND from service/controller)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        Map<String, Object> details = Map.of("error", ex.getReason() == null ? "Request failed" : ex.getReason());
        return buildError(status, "Request failed", details);
    }

    // 4) Catch-all fallback (don’t leak internals)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        Map<String, Object> details = Map.of(
                "error", "Unexpected server error"
        );
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", details);
    }

    // Helper: consistent error shape
    private ResponseEntity<Object> buildError(HttpStatus status, String message, Map<String, ?> details) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("message", message);
        body.put("details", details);
        return ResponseEntity.status(status).body(body);
    }
}
