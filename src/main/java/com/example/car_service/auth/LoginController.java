package com.example.car_service.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {

    // ABSTRACTION: Delegates authentication logic to AuthService.
    private final AuthService authService;

    // ENCAPSULATION & ABSTRACTION: Takes a LoginRequest DTO, verifies credentials via AuthService, and returns session data.
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest req) {
        return authService.login(req);
    }

    // ABSTRACTION: Handles seeding a default admin account if none exists.
    @PostMapping("/seed-admin")
    public Map<String, Object> seedAdmin(@RequestBody Map<String, String> body) {
        return authService.seedAdmin(body);
    }

    // ENCAPSULATION: Bundles email, password, and requested role into one object for login verification.
    @Data
    public static class LoginRequest {
        private String email;
        private String password;
        private String role;
    }
}
