package com.example.car_service.exception;

/**
 * Custom exception thrown when trying to create a record that already exists.
 *
 * Example: Registering a user with an email that is already in the database.
 *   throw new DuplicateResourceException("Email already registered: " + email);
 *
 * The GlobalExceptionHandler catches this and returns HTTP 409 Conflict.
 *
 * OOP Concept: INHERITANCE — extends RuntimeException (parent class).
 */
public class DuplicateResourceException extends RuntimeException {

    // Constructor takes a message describing the duplicate field
    public DuplicateResourceException(String message) {
        super(message); // Pass message up to the RuntimeException parent
    }
}