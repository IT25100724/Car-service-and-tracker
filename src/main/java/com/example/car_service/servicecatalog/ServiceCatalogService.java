package com.example.car_service.servicecatalog;

import java.util.List;

// Service interface for managing service categories
public interface ServiceCatalogService {

    ServiceCatalog createService(ServiceCatalog service);

    ServiceCatalog getServiceById(Long id);

    List<ServiceCatalog> getAllServices();

    List<ServiceCatalog> getActiveServices();

    List<ServiceCatalog> getServicesByCategory(String category);

    ServiceCatalog updateService(Long id, ServiceCatalog service);

    void deleteService(Long id);

    ServiceCatalog toggleActive(Long id);
}