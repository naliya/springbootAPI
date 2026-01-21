package com.learning.springapi.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.AuthenticationException;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // 1) Validation errors from @Valid (e.g., @NotBlank, @Email, @NotNull)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> fieldErrors.put(err.getField(), err.getDefaultMessage()));

        log.error("Unhandled exception occurred 1", ex);
        return buildError(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors);
    }

    // 2) Bad JSON / wrong types (e.g., "age": "" when age is Integer)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleBadJson(HttpMessageNotReadableException ex) {
        // Keep message simple and safe for clients
        Map<String, Object> error = Map.of(
                "error", "Invalid request body. Check JSON format and field types (e.g., age must be a number)."
        );

        log.error("Unhandled exception occurred 2", ex);
        return buildError(HttpStatus.BAD_REQUEST, "Malformed JSON or wrong field type", error);
    }

    // 3) Your thrown ResponseStatusException (e.g., NOT_FOUND from service/controller)
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex) {
        HttpStatus status = HttpStatus.valueOf(ex.getStatusCode().value());
        Map<String, Object> error = Map.of("error", ex.getReason() == null ? "Request failed" : ex.getReason());

        log.error("Unhandled exception occurred 3", ex);
        return buildError(status, "Request failed", error);
    }

    // 4) Catch-all fallback (don’t leak internals)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception ex) {
        Map<String, Object> error = Map.of(
                "error", "Unexpected server error"
        );

        log.error("Unhandled exception occurred 4", ex);
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Server error", error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuth(AuthenticationException ex) {
        Map<String, Object> error = Map.of("error", "Invalid username or password");

        log.error("Unhandled exception occurred 5", ex);
        return buildError(HttpStatus.UNAUTHORIZED, "Unauthorized", error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return buildError(HttpStatus.FORBIDDEN, "Forbidden",
                Map.of("error", "You don't have permission to access this resource"));
    }

    // Helper: consistent error shape
    private ResponseEntity<Object> buildError(HttpStatus status, String message, Map<String, ?> error) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", Instant.now().toString());
        body.put("status", status.value());
        body.put("message", message);
        body.put("error", error);
        return ResponseEntity.status(status).body(body);
    }
}
