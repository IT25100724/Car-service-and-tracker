package com.example.car_service.user;

// Import JPA
import jakarta.persistence.*;

import java.time.LocalDateTime;

// Parent class for entities, providing primary key and creation timestamp
@MappedSuperclass
// Abstract class
public abstract class BaseEntity {

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto generate the primary key
    private Long id;



    private LocalDateTime createdAt;

    // Auto create date and time when the saving user
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }


    // Encapsulation (Below using getter/setter)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
