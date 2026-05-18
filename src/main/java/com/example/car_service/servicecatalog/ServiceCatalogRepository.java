package com.example.car_service.servicecatalog;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

// Repository for service catalog operations
public interface ServiceCatalogRepository extends JpaRepository<ServiceCatalog, Long> {

    List<ServiceCatalog> findByActive(boolean active);

    List<ServiceCatalog> findByCategory(String category);
}