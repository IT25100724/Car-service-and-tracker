package com.example.car_service.servicecatalog;

import jakarta.persistence.*;
import lombok.*;

// Represents a service offered by the garage
@Entity
@Table(name = "service_catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCatalog {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Service name
    @Column(name = "service_name", nullable = false)
    private String serviceName;

    // Service description
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // Service price
    @Column(name = "price", nullable = false)
    private Double price;

    // Estimated duration in minutes
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    // Service category
    @Column(name = "category", length = 50)
    private String category;

    // Availability status
    @Column(name = "active")
    @Builder.Default
    private boolean active = true;
}
