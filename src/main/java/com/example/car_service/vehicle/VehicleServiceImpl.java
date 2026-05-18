package com.example.car_service.vehicle;

import com.example.car_service.exception.DuplicateResourceException;
import com.example.car_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

//VEHICLE SERVICE IMPLEMENTATION


@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;

    @Override
    public Vehicle addVehicle(Vehicle vehicle) {
        // Validate license plate is unique
        if (vehicleRepository.existsByLicensePlate(vehicle.getLicensePlate())) {
            throw new DuplicateResourceException("Vehicle with license plate '" + vehicle.getLicensePlate() + "' already exists");
        }
        return vehicleRepository.save(vehicle);
    }

    @Override
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    @Override
    public List<Vehicle> getVehiclesByCustomerId(Long customerId) {
        return vehicleRepository.findByCustomerId(customerId);
    }

    @Override
    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        // Find existing or throw error
        Vehicle existing = getVehicleById(id);
        
        // Update fields
        existing.setLicensePlate(vehicle.getLicensePlate());
        existing.setBrand(vehicle.getBrand());
        existing.setModel(vehicle.getModel());
        existing.setYear(vehicle.getYear());
        existing.setMileage(vehicle.getMileage());
        existing.setNextServiceMileage(vehicle.getNextServiceMileage());
        existing.setCustomerId(vehicle.getCustomerId());
        
        return vehicleRepository.save(existing);
    }

    @Override
    public void deleteVehicle(Long id) {
        //  it exists first
        getVehicleById(id);
        vehicleRepository.deleteById(id);
    }
}