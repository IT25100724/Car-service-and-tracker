package com.example.car_service.exception;

/**
 * Custom exception thrown when a requested resource is not found in the database.
 *
 * Example usage:
 *   throw new ResourceNotFoundException("User not found with id: " + id);
 *
 * The GlobalExceptionHandler catches this and returns HTTP 404 Not Found.
 *
 * OOP Concept: This is an example of INHERITANCE.
 * RuntimeException is the parent class. We extend it to create a specific type.
 */
public class ResourceNotFoundException extends RuntimeException {

    // Constructor takes a message describing what was not found
    public ResourceNotFoundException(String message) {
        super(message); // Pass the message to the parent RuntimeException class
    }
}