package com.example.car_service.exception;

import java.time.LocalDateTime;

/**
 * This class represents the structured JSON error response sent back to the client
 * when something goes wrong (e.g., resource not found, validation error).
 *
 * Example JSON response:
 * {
 *   "message": "User not found with id: 5",
 *   "path": "/api/users/5",
 *   "timestamp": "2024-05-15T10:30:00"
 * }
 *
 * OOP Concept: ENCAPSULATION — fields are private, accessed via getters.
 */
public class ErrorResponse {

    // The error message describing what went wrong
    private String message;

    // The URL path that caused the error
    private String path;

    // When the error occurred (auto-set to current time)
    private LocalDateTime timestamp;

    /**
     * Constructor — creates an ErrorResponse with the given message and path.
     * Timestamp is automatically set to the current date and time.
     *
     * @param message  Description of the error
     * @param path     The API endpoint that was called
     */
    public ErrorResponse(String message, String path) {
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now(); // Record when the error happened
    }

    // ---- Getters (needed for JSON serialization) ----

    public String getMessage() { return message; }
    public String getPath()    { return path; }
    public LocalDateTime getTimestamp() { return timestamp; }
}