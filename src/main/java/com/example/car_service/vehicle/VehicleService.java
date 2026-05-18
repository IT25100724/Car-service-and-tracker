package com.example.car_service.vehicle;

import java.util.List;

//VEHICLE SERVICE INTERFACE

public interface VehicleService {
    Vehicle addVehicle(Vehicle vehicle);
    Vehicle getVehicleById(Long id);
    List<Vehicle> getAllVehicles();
    List<Vehicle> getVehiclesByCustomerId(Long customerId);
    Vehicle updateVehicle(Long id, Vehicle vehicle);
    void deleteVehicle(Long id);
}
