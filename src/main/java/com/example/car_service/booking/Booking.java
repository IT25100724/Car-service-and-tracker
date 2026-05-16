package com.example.car_service.booking;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/**
 * BOOKING ENTITY — MODULE 5: BOOKING MANAGEMENT
 * ===============================================
 * Represents an appointment to service a vehicle.
 * Maps to the "bookings" table.
 *
 * OOP Concept: FOREIGN KEYS (Relational Mapping)
 * Booking connects multiple entities together:
 * - Which car? (vehicleId)
 * - What job? (serviceId)
 * - Who is doing it? (staffId)
 */
@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** Foreign Key linking to the vehicles table */
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    /** Foreign Key linking to the service_catalog table */
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    /** Foreign Key linking to the staff table (who is doing the repair) */
    @Column(name = "staff_id")
    private Long staffId;

    /** When the appointment is scheduled for */
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    /** Current state of the booking (e.g., PENDING, COMPLETED) */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    /** Total cost of the service */
    @Column(name = "total_amount")
    private Double totalAmount;

    /** Has it been paid? */
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    /** How it was paid (e.g., "CASH", "CREDIT_CARD") */
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    /** Additional notes or problems described by the customer */
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /** Sets defaults before saving */
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = BookingStatus.PENDING;
        if (paymentStatus == null) paymentStatus = PaymentStatus.UNPAID;
    }
}
