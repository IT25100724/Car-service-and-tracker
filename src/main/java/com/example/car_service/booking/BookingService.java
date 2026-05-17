package com.example.car_service.booking;

import java.util.List;


public interface BookingService {
    Booking createBooking(Booking booking);
    Booking getBookingById(Long id);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByVehicle(Long vehicleId);
    List<Booking> getBookingsByCustomer(Long customerId);
    List<Booking> getBookingsByStaff(Long staffId);
    List<Booking> getBookingsByStatus(BookingStatus status);
    
    Booking updateBooking(Long id, Booking booking);
    Booking updateStatus(Long id, BookingStatus status);
    Booking completeBooking(Long id, Integer currentMileage, Integer nextServiceMileage);
    Booking processPayment(Long id, String paymentMethod);
    
    void deleteBooking(Long id);
}
