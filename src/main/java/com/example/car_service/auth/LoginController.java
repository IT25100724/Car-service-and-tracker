package com.example.car_service.auth;

//Import that brings in all Spring MVC annotations needed for REST controllers
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// REST Controller for handling user login and database initialization
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class LoginController {

    // Encapsulation (Private Attribute)
    private final AuthService authService;

    // Constructor
    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint for authenticating users
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    // Endpoint to seed the default Admin user
    @PostMapping("/seed-admin")
    public Map<String, Object> seedAdmin(@RequestBody Map<String, String> body) {
        return authService.seedAdmin(body);
    }

    // Data Transfer Object (DTO) representing a login request
    public static class LoginRequest {
        private String email;
        private String password;
        private String role;

        // Default Constructor
        public LoginRequest() {
        }

        // Encapsulation (Using below getter/setter)
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }
    }
}
