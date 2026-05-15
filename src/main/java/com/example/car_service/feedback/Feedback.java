package com.example.car_service.feedback;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

//Entity class representing customer feedback for a completed car service.
// Stores ratings, comments, and related booking/customer details.

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

    //ID of the related service booking
    @Column(name = "booking_id", nullable = false)
    private Long bookingId;

    //ID of the customer who submitted the feedback
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    //Customer rating value(1 to 5)
    @Column(name = "rating", nullable = false)
    private Integer rating;

    //Additional review comment provided by the customer
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    //Automatically sets the creation time before inserting the record.
    @PrePersist
    protected void onCreate() {

        createdAt = LocalDateTime.now();
    }
}
