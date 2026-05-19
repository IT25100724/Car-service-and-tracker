package com.example.car_service.user;

import com.fasterxml.jackson.annotation.JsonIgnore; // Import JSON
import jakarta.persistence.*; // Import JPA

import java.time.LocalDateTime;

// This class maps to a DB tables
// Represents a registered user in the system (Admin or Customer)
@Entity
@Table(name = "users")
// Inheritance from BaseEntity
public class User extends BaseEntity {

    // Encapsulation (Attribute as Private)
    private String username;
    private String email;

    @JsonIgnore  // Password for invisible to the frontend
    private String passwordHash;



    @Enumerated(EnumType.STRING)
    private UserRole role;

    private Long customerId;
    private Boolean isActive = true;
    private LocalDateTime lastLogin;
    private LocalDateTime updatedAt;

    // Auto-set the update date and time before database updates
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Default constructor for JPA
    public User() {
    }

    // Parameterized constructor
    public User(String username, String email, String passwordHash, UserRole role, Long customerId, Boolean isActive) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.customerId = customerId;
        this.isActive = isActive;
    }


    // Encapsulation (below using getter/setter)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Defines roles for access control
    public enum UserRole {
        ADMIN,
        CUSTOMER
    }
}
