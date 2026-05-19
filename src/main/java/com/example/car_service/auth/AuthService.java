package com.example.car_service.auth;

import com.example.car_service.auth.LoginController.LoginRequest;
import com.example.car_service.user.User;
import com.example.car_service.user.User.UserRole;
import com.example.car_service.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Service class handling authentication logic like login and admin user seeding
@Service
public class AuthService {

    // Encapsulation
    private final UserRepository userRepository;

    // Constructor
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Verify credentials, check account status, and return user profile details
    public Map<String, Object> login(LoginRequest req) {
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail());

        // Check email exist or not
        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        // Check customer status (Active or not)
        if (!user.getIsActive()) {
            throw new RuntimeException("Account is disabled. Contact admin.");
        }

        // Check password valid or invalid
        if (!req.getPassword().equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Check the user role and that is valid or not
        String requestedRole = req.getRole();
        if (requestedRole != null && !requestedRole.isBlank()) {
            boolean isCustomer = user.getRole() == UserRole.CUSTOMER;
            boolean isAdmin = user.getRole() == UserRole.ADMIN;

            if ("customer".equalsIgnoreCase(requestedRole) && !isCustomer) {
                throw new RuntimeException("Not a customer account");
            }
            if ("admin".equalsIgnoreCase(requestedRole) && !isAdmin) {
                throw new RuntimeException("Not an admin account");
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("userId", user.getId());
        response.put("email", user.getEmail());
        response.put("username", user.getUsername());
        response.put("role", user.getRole().name());
        response.put("customerId", user.getCustomerId());
        return response;
    }

    // Pre-populate database with a default Admin account if none exists
    public Map<String, Object> seedAdmin(Map<String, String> body) {
        boolean adminExists = userRepository.findAll()
                .stream()
                .anyMatch(u -> u.getRole() == UserRole.ADMIN);

        // Check Admin already exists
        if (adminExists) {
            return Map.of("message", "Admin account already exists.");
        }

        // Create Admin for manual
        String email = body.getOrDefault("email", "admin@carservice.com");
        String username = body.getOrDefault("username", "admin");
        String password = body.getOrDefault("password", "Admin@1234");

        // Create new user (Admin) object
        User admin = new User(
                username,
                email,
                password,
                UserRole.ADMIN,
                null,
                true
        );

        // save that in DB
        userRepository.save(admin);

        // Return the confirmation
        return Map.of(
                "message", "Admin account created successfully",
                "email", email,
                "password", password
        );
    }
}
