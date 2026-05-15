package com.example.car_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================
 *  CAR SERVICE MANAGEMENT SYSTEM
 *  SE1020 - Object Oriented Programming Project
 * ============================================================
 *
 *  This is the main entry point of the Spring Boot application.
 *  @SpringBootApplication tells Spring Boot to:
 *    1. Scan all classes in this package and sub-packages
 *    2. Auto-configure database, web server, etc.
 *    3. Start the embedded Tomcat server on port 8080
 *
 *  MODULES COVERED:
 *  1. User Management       --> /api/users
 *  2. Vehicle Management    --> /api/vehicles
 *  3. Staff Management      --> /api/staff
 *  4. Service Category Mgmt --> /api/services
 *  5. Booking Management    --> /api/bookings
 *  6. Review Management     --> /api/feedback
 *
 *  HOW TO RUN:
 *  Run this main() method or use: mvn spring-boot:run
 *  Then open: http://localhost:8080
 */
@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {
        // SpringApplication.run() boots the entire Spring context
        // and starts the embedded Tomcat web server
        SpringApplication.run(CarServiceApplication.class, args);
    }
}
