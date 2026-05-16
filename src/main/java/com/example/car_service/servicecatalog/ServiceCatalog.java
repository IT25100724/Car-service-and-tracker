package com.example.car_service.servicecatalog;

import jakarta.persistence.*;
import lombok.*;

/**
 * SERVICE CATALOG ENTITY — MODULE 4: SERVICE CATEGORY MANAGEMENT
 * ===============================================================
 * Represents a type of service the garage offers (e.g., Oil Change, Brake Repair).
 * Maps to the "service_catalog" table.
 *
 * OOP Concept: DATA ABSTRACTION
 * Only the essential details of a service are exposed here.
 */
@Entity
@Table(name = "service_catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCatalog {

    /** Primary key */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Name of the service (e.g., "Full Wash") */
    @Column(name = "service_name", nullable = false)
    private String serviceName;

    /** Description of what the service includes */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /** Cost of the service */
    @Column(name = "price", nullable = false)
    private Double price;

    /** Estimated time it takes */
    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    /** Category grouping (e.g., "Maintenance", "Cleaning") */
    @Column(name = "category", length = 50)
    private String category;

    /** Is this service currently offered? */
    @Column(name = "active")
    @Builder.Default
    private boolean active = true;
}
