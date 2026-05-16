package com.example.car_service.vehicle;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    //Auto-generated primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //ID of the customer owns this vehicle
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    //  license plate number
    @Column(name = "license_plate", nullable = false, unique = true)
    private String licensePlate;

    // Brand of the car
    @Column(name = "brand", nullable = false)
    private String brand;

    // Model of the car
    @Column(name = "model", nullable = false)
    private String model;

    // Manufacturing year
    @Column(name = "year", nullable = false)
    private int year;

    // Current mileage of the vehicle
    @Column(name = "mileage")
    private Integer mileage;

    // mileage for the next  service
    @Column(name = "next_service_mileage")
    private Integer nextServiceMileage;

    // Automatically set timestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Called by JPA before creating the record
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
