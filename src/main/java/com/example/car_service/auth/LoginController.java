package com.example.car_service.auth;

import com.example.car_service.user.User;
import com.example.car_service.user.UserRepository;
import com.example.car_service.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * LOGIN CONTROLLER
 * =================
 * Handles user authentication (login) for the Car Service system.
 *
 * Endpoints:
 *   POST /api/auth/login       --> Login with email + password
 *   POST /api/auth/seed-admin  --> Create the first admin account (one-time setup)
 *
 * How login works:
 * 1. Client sends email + password in the request body (JSON).
 * 2. We look up the user by email in the database.
 * 3. We compare the plain-text password (no hashing for simplicity).
 * 4. If correct, we return the user's info (id, role, etc.).
 * 5. The frontend stores this info and uses it to show the right dashboard.
 *
 * OOP Concept: This is a CONTROLLER — handles HTTP requests and delegates to repositories/services.
 * @RequiredArgsConstructor = Lombok generates a constructor with all final fields (UserRepository).
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Allow requests from any frontend (browser, Postman, etc.)
public class LoginController {

    // Injected by Spring (Dependency Injection) — used to query the users table
    private final UserRepository userRepository;

    /**
     * LOGIN ENDPOINT
     * ==============
     * Authenticates a user by checking their email and password.
     *
     * @param req  Contains: email, password, and optional role
     * @return     User details (id, email, username, role) on success, or error message
     *
     * HTTP Method: POST
     * URL: /api/auth/login
     *
     * Example request body:
     * { "email": "admin@carservice.com", "password": "Admin@1234", "role": "admin" }
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest req) {

        // Step 1: Look up user by email address
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail());

        // Step 2: If no user found with that email, reject login
        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        User user = userOpt.get();

        // Step 3: Check if the account is active (not disabled)
        if (!user.getIsActive()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Account is disabled. Contact admin."));
        }

        // Step 4: Compare the entered password with the stored plain-text password
        if (!req.getPassword().equals(user.getPasswordHash())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }

        // Step 5: Validate the role if the client specified one (e.g., admin login page)
        String requestedRole = req.getRole();
        if (requestedRole != null && !requestedRole.isBlank()) {
            boolean isCustomer = user.getRole() == UserRole.CUSTOMER;
            boolean isAdmin    = user.getRole() == UserRole.ADMIN;

            // Reject if customer tries to log in via admin portal
            if ("customer".equalsIgnoreCase(requestedRole) && !isCustomer) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Not a customer account"));
            }
            // Reject if admin tries to log in via customer portal
            if ("admin".equalsIgnoreCase(requestedRole) && !isAdmin) {
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("message", "Not an admin account"));
            }
        }

        // Step 6: Login successful — return user info to the frontend
        Map<String, Object> response = new HashMap<>();
        response.put("userId",     user.getId());
        response.put("email",      user.getEmail());
        response.put("username",   user.getUsername());
        response.put("role",       user.getRole().name());    // "ADMIN" or "CUSTOMER"
        response.put("customerId", user.getCustomerId());     // null for admin users
        return ResponseEntity.ok(response);
    }

    /**
     * SEED ADMIN ENDPOINT
     * ====================
     * Creates the first admin account if no admin exists.
     * Used for initial setup — run this once after the database is empty.
     *
     * HTTP Method: POST
     * URL: /api/auth/seed-admin
     *
     * Example request body (all fields optional, defaults shown):
     * { "email": "admin@carservice.com", "username": "admin", "password": "Admin@1234" }
     */
    @PostMapping("/seed-admin")
    public ResponseEntity<Map<String, Object>> seedAdmin(@RequestBody Map<String, String> body) {

        // Check if any admin account already exists
        boolean adminExists = userRepository.findAll()
                .stream()
                .anyMatch(u -> u.getRole() == UserRole.ADMIN);

        if (adminExists) {
            return ResponseEntity.ok(Map.of("message", "Admin account already exists."));
        }

        // Use provided values or fall back to defaults
        String email    = body.getOrDefault("email",    "admin@carservice.com");
        String username = body.getOrDefault("username", "admin");
        String password = body.getOrDefault("password", "Admin@1234");

        // Create and save the admin user (plain-text password)
        User admin = User.builder()
                .email(email)
                .username(username)
                .passwordHash(password)  // Stored as plain text for simplicity
                .role(UserRole.ADMIN)
                .isActive(true)
                .build();

        userRepository.save(admin);

        return ResponseEntity.ok(Map.of(
                "message",  "Admin account created successfully",
                "email",    email,
                "password", password
        ));
    }
}
