package com.example.car_service.auth;

import lombok.Data;

/**
 * LOGIN REQUEST DATA TRANSFER OBJECT (DTO)
 * ==========================================
 * This class holds the data sent by the client when logging in.
 * It is NOT stored in the database — it only carries data from the HTTP request body.
 *
 * The client sends JSON like this:
 * {
 *   "email": "admin@carservice.com",
 *   "password": "Admin@1234",
 *   "role": "admin"
 * }
 *
 * OOP Concept: ENCAPSULATION — private fields with getters/setters (via @Data from Lombok).
 * DTO Pattern — a simple class used to transfer data between layers.
 */
@Data
public class LoginRequest {

    // The user's email address (used to look up the account)
    private String email;

    // The password entered by the user (compared against stored value)
    private String password;

    // Optional: expected role ("admin" or "customer") for role validation
    private String role;
}
