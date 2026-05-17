package com.example.car_service.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * GLOBAL EXCEPTION HANDLER
 * =========================
 * This class intercepts all exceptions thrown anywhere in the application
 * and converts them into a clean JSON error response.
 *
 * Without this, Spring would return a confusing default error page.
 * With this, every error returns a structured JSON like:
 * {
 *   "message": "User not found with id: 5",
 *   "path": "/api/users/5",
 *   "timestamp": "..."
 * }
 *
 * OOP Concept: This is a centralized error handling class — Single Responsibility Principle.
 * @RestControllerAdvice tells Spring this class handles errors for all @RestController classes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles: ResourceNotFoundException
     * Triggered when: A record with the given ID doesn't exist in the database.
     * HTTP Status: 404 Not Found
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(ex.getMessage(), req.getRequestURI()));
    }

    /**
     * Handles: DuplicateResourceException
     * Triggered when: Trying to insert a record with a value that must be unique (e.g., email).
     * HTTP Status: 409 Conflict
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateResourceException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(ex.getMessage(), req.getRequestURI()));
    }

    /**
     * Handles: No matching URL endpoint found.
     * Triggered when: Client calls a URL that doesn't exist (e.g., /api/unknown).
     * HTTP Status: 404 Not Found
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoHandler(
            NoHandlerFoundException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("The requested resource was not found.", req.getRequestURI()));
    }

    /**
     * Handles: HTTP method not supported (e.g., sending DELETE to a GET-only endpoint).
     * HTTP Status: 405 Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(
            HttpRequestMethodNotSupportedException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ErrorResponse("This action is not supported on this resource.", req.getRequestURI()));
    }

    /**
     * Handles: IllegalArgumentException
     * Triggered when: Invalid data is provided (e.g., rating not between 1 and 5).
     * HTTP Status: 400 Bad Request
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            IllegalArgumentException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), req.getRequestURI()));
    }

    /**
     * Handles: IllegalStateException
     * Triggered when: Business rule violated (e.g., submitting review for non-completed booking).
     * HTTP Status: 422 Unprocessable Entity
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalState(
            IllegalStateException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ErrorResponse(ex.getMessage(), req.getRequestURI()));
    }

    /**
     * Handles: All other RuntimeExceptions (catch-all for unexpected errors).
     * HTTP Status: 400 Bad Request
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntime(
            RuntimeException ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred.",
                        req.getRequestURI()));
    }

    /**
     * Handles: Any other Exception (the absolute catch-all — last resort).
     * HTTP Status: 500 Internal Server Error
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(
            Exception ex, HttpServletRequest req) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Something went wrong. Please try again later.", req.getRequestURI()));
    }
}