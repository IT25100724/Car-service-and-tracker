package com.example.car_service.servicecatalog;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// REST API for service category management
@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ServiceCatalogController {

    private final ServiceCatalogService serviceCatalogService;

    // Create a new service
    @PostMapping
    public ResponseEntity<ServiceCatalog> createService(@RequestBody ServiceCatalog service) {
        return new ResponseEntity<>(serviceCatalogService.createService(service), HttpStatus.CREATED);
    }

    // Get service by ID
    @GetMapping("/{id}")
    public ResponseEntity<ServiceCatalog> getServiceById(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCatalogService.getServiceById(id));
    }

    // Get all services
    @GetMapping
    public ResponseEntity<List<ServiceCatalog>> getAllServices() {
        return ResponseEntity.ok(serviceCatalogService.getAllServices());
    }

    // Get active services
    @GetMapping("/active")
    public ResponseEntity<List<ServiceCatalog>> getActiveServices() {
        return ResponseEntity.ok(serviceCatalogService.getActiveServices());
    }

    // Get services by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ServiceCatalog>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(serviceCatalogService.getServicesByCategory(category));
    }

    // Update service details
    @PutMapping("/{id}")
    public ResponseEntity<ServiceCatalog> updateService(@PathVariable Long id, @RequestBody ServiceCatalog service) {
        return ResponseEntity.ok(serviceCatalogService.updateService(id, service));
    }

    // Delete service
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteService(@PathVariable Long id) {
        serviceCatalogService.deleteService(id);
        return ResponseEntity.ok("Service deleted successfully");
    }

    // Toggle service active status
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<ServiceCatalog> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(serviceCatalogService.toggleActive(id));
    }
}