package com.example.car_service.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByVehicleId(Long vehicleId);

    List<Booking> findByStaffId(Long staffId);

    List<Booking> findByStatus(BookingStatus status);


    List<Booking> findByVehicleIdIn(List<Long> vehicleIds);
}
