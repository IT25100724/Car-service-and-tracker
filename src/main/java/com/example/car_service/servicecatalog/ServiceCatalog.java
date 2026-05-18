package com.example.car_service.servicecatalog;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "service_catalog")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceCatalog {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "service_name", nullable = false)
    private String serviceName;


    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "category", length = 50)
    private String category;

    @Column(name = "active")
    @Builder.Default
    private boolean active = true;
}
