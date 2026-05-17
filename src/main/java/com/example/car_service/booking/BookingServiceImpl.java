package com.example.car_service.booking;

import com.example.car_service.exception.ResourceNotFoundException;
import com.example.car_service.vehicle.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + id));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByVehicle(Long vehicleId) {
        return bookingRepository.findByVehicleId(vehicleId);
    }


    @Override
    public List<Booking> getBookingsByCustomer(Long customerId) {
        List<Long> vehicleIds = vehicleRepository.findByCustomerId(customerId)
                .stream()
                .map(v -> v.getId())
                .collect(Collectors.toList());

        if (vehicleIds.isEmpty()) return List.of();
        
        return bookingRepository.findByVehicleIdIn(vehicleIds);
    }

    @Override
    public List<Booking> getBookingsByStaff(Long staffId) {
        return bookingRepository.findByStaffId(staffId);
    }

    @Override
    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public Booking updateBooking(Long id, Booking booking) {
        Booking existing = getBookingById(id);
        existing.setVehicleId(booking.getVehicleId());
        existing.setServiceId(booking.getServiceId());
        existing.setStaffId(booking.getStaffId());
        existing.setBookingDate(booking.getBookingDate());
        existing.setTotalAmount(booking.getTotalAmount());
        existing.setNotes(booking.getNotes());
        
        if (booking.getStatus() != null) existing.setStatus(booking.getStatus());
        if (booking.getPaymentStatus() != null) existing.setPaymentStatus(booking.getPaymentStatus());
        if (booking.getPaymentMethod() != null) existing.setPaymentMethod(booking.getPaymentMethod());
        
        return bookingRepository.save(existing);
    }

    @Override
    public Booking updateStatus(Long id, BookingStatus status) {
        Booking booking = getBookingById(id);
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking completeBooking(Long id, Integer currentMileage, Integer nextServiceMileage) {
        Booking booking = getBookingById(id);
        booking.setStatus(BookingStatus.COMPLETED);
        
        vehicleRepository.findById(booking.getVehicleId()).ifPresent(vehicle -> {
            if (currentMileage != null) vehicle.setMileage(currentMileage);
            if (nextServiceMileage != null) vehicle.setNextServiceMileage(nextServiceMileage);
            vehicleRepository.save(vehicle);
        });
        
        return bookingRepository.save(booking);
    }

    @Override
    public Booking processPayment(Long id, String paymentMethod) {
        Booking booking = getBookingById(id);
        booking.setPaymentStatus(PaymentStatus.PAID);
        booking.setPaymentMethod(paymentMethod);
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteBooking(Long id) {
        getBookingById(id);
        bookingRepository.deleteById(id);
    }
}