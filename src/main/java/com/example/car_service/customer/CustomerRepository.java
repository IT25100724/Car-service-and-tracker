package com.example.car_service.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Repository interface for Customer entity database operations
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Find a customer by their email address
    Optional<Customer> findByEmail(String email);
    

    // Check if a customer email already exists
    boolean existsByEmail(String email);
}
