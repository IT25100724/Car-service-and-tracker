package com.example.car_service.booking;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


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

    
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;

    
    @Column(name = "service_id", nullable = false)
    private Long serviceId;

    
    @Column(name = "staff_id")
    private Long staffId;

    
    @Column(name = "booking_date", nullable = false)
    private LocalDateTime bookingDate;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private BookingStatus status = BookingStatus.PENDING;

    
    @Column(name = "total_amount")
    private Double totalAmount;

    
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;

    
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (status == null) status = BookingStatus.PENDING;
        if (paymentStatus == null) paymentStatus = PaymentStatus.UNPAID;
    }
}
