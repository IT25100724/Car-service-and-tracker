package com.example.car_service.auth;

import com.example.car_service.user.User;
import com.example.car_service.user.UserRepository;
import com.example.car_service.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    // Encapsulation
    private final UserRepository userRepository;

    // Polymorphism
    @Override
    public Map<String, Object> login(LoginRequest req) {
        Optional<User> userOpt = userRepository.findByEmail(req.getEmail());

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User user = userOpt.get();

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is disabled. Contact admin.");
        }

        if (!req.getPassword().equals(user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

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

    // Polymorphism
    @Override
    public Map<String, Object> seedAdmin(Map<String, String> body) {

        boolean adminExists = userRepository.findAll()
                .stream()
                .anyMatch(u -> u.getRole() == UserRole.ADMIN);

        if (adminExists) {
            return Map.of("message", "Admin account already exists.");
        }

        String email = body.getOrDefault("email", "admin@carservice.com");
        String username = body.getOrDefault("username", "admin");
        String password = body.getOrDefault("password", "Admin@1234");

        User admin = User.builder()
                .email(email)
                .username(username)
                .passwordHash(password)
                .role(UserRole.ADMIN)
                .isActive(true)
                .build();

        userRepository.save(admin);

        return Map.of(
                "message", "Admin account created successfully",
                "email", email,
                "password", password
        );
    }
}
