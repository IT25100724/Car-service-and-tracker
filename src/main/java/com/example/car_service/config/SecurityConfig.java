package com.example.car_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;

/**
 * SECURITY CONFIGURATION
 * =======================
 * This class configures Spring Security for the application.
 *
 * For this assignment project:
 * - All API endpoints are OPEN (no login required via Spring Security).
 * - Authentication logic is handled manually in LoginController (plain-text password comparison).
 * - CSRF protection is disabled (safe for REST APIs that don't use browser sessions).
 * - CORS is enabled so the frontend HTML pages can call the API from the browser.
 *
 * WHY SPRING SECURITY IS STILL HERE:
 * The spring-boot-starter-security dependency adds Spring Security automatically.
 * Without this config, Spring Security would block ALL requests by default.
 * This config overrides that behavior to allow everything through.
 *
 * OOP Concept: @Configuration and @Bean are Spring's way of defining objects
 * (beans) that the framework manages — this is Dependency Injection.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Password encoder that does NOT hash passwords.
     * Passwords are stored and compared as plain text.
     *
     * NOTE: In real production systems, use BCryptPasswordEncoder for security.
     * For this assignment, plain text is used for simplicity.
     *
     * @SuppressWarnings("deprecation") silences the compiler warning about NoOpPasswordEncoder.
     */
    @Bean
    @SuppressWarnings("deprecation")
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Defines which HTTP requests are allowed and how.
     *
     * Configuration:
     * - CORS: Allow requests from any origin (needed for browser-based frontend)
     * - CSRF: Disabled (not needed for stateless REST APIs)
     * - Session: Stateless (no server-side sessions; each request is independent)
     * - All requests: Permitted (authentication checked manually in controllers)
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Enable CORS using our custom configuration below
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))

            // Disable CSRF (not needed for REST APIs)
            .csrf(csrf -> csrf.disable())

            // Don't create HTTP sessions — each API call is independent
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // Allow ALL requests without authentication
            // (Login verification is done manually in LoginController)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    /**
     * CORS (Cross-Origin Resource Sharing) Configuration
     * ----------------------------------------------------
     * CORS controls which websites can call our API from a browser.
     * We allow ALL origins (*) so the HTML frontend can talk to the backend
     * even when opened directly as a file or from a different port.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Allow requests from any origin
        configuration.setAllowedOrigins(Arrays.asList("*"));

        // Allow these HTTP methods
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));

        // Allow these HTTP headers in requests
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));

        // Apply this CORS config to ALL endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
