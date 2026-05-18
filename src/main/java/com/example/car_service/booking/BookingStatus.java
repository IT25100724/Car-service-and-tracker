package com.example.car_service.booking;


public enum BookingStatus {
    PENDING,       // Customer requested it
    CONFIRMED,     // Garage accepted it
    IN_PROGRESS,   // Car is currently being worked on
    COMPLETED,     // Job is done
    CANCELLED      // Job was cancelled
}
