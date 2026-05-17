package com.example.car_service.servicecatalog;

import com.example.car_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

// Service implementation for service catalog operations
@Service
@RequiredArgsConstructor
public class ServiceCatalogServiceImpl implements ServiceCatalogService {

    private final ServiceCatalogRepository serviceCatalogRepository;

    @Override
    public ServiceCatalog createService(ServiceCatalog service) {
        return serviceCatalogRepository.save(service);
    }

    @Override
    public ServiceCatalog getServiceById(Long id) {
        return serviceCatalogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + id));
    }

    @Override
    public List<ServiceCatalog> getAllServices() {
        return serviceCatalogRepository.findAll();
    }

    @Override
    public List<ServiceCatalog> getActiveServices() {
        return serviceCatalogRepository.findByActive(true);
    }

    @Override
    public List<ServiceCatalog> getServicesByCategory(String category) {
        return serviceCatalogRepository.findByCategory(category);
    }

    @Override
    public ServiceCatalog updateService(Long id, ServiceCatalog service) {
        ServiceCatalog existing = getServiceById(id);
        existing.setServiceName(service.getServiceName());
        existing.setDescription(service.getDescription());
        existing.setPrice(service.getPrice());
        existing.setDurationMinutes(service.getDurationMinutes());
        existing.setCategory(service.getCategory());
        existing.setActive(service.isActive());
        return serviceCatalogRepository.save(existing);
    }

    @Override
    public void deleteService(Long id) {
        getServiceById(id);
        serviceCatalogRepository.deleteById(id);
    }

    // Toggle service active status
    @Override
    public ServiceCatalog toggleActive(Long id) {
        ServiceCatalog service = getServiceById(id);
        service.setActive(!service.isActive());
        return serviceCatalogRepository.save(service);
    }
}