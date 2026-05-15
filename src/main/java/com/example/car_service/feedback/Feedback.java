package com.example.car_service.feedback;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * FEEDBACK ENTITY — MODULE 6: REVIEW MANAGEMENT
 * ===============================================
 * Represents a customer's review for a completed service.
 * Maps to the "feedback" table.
 *
 * OOP Concept: ENCAPSULATION
 * All properties are private and accessed via Lombok-generated getters/setters.
 */
@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Which booking is this review for? */
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    /** Who wrote the review? */
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    /** Star rating out of 5 */
    @Column(name = "rating", nullable = false)
    private Integer rating;

    /** Written review comment */
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
