package com.example.car_service.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


// Repository interface for User entity database operations
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by their username
    Optional<User> findByUsername(String username);

    // Find a user by their email address
    Optional<User> findByEmail(String email);

    // Find a user account by their associated customer ID
    Optional<User> findByCustomerId(Long customerId);

    // Check if a username already exists
    boolean existsByUsername(String username);

    // Check if an email already exists
    boolean existsByEmail(String email);
}
