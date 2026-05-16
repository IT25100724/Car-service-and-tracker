package com.example.car_service.vehicle;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

//VEHICLE REPOSITORY

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    // vehicles owned by  customer
    List<Vehicle> findByCustomerId(Long customerId);

    // Check  license plate
    boolean existsByLicensePlate(String licensePlate);
}
