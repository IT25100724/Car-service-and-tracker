package com.example.car_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CarServiceApplication {

    public static void main(String[] args) {
        // SpringApplication.run() boots the entire Spring context
        // and starts the embedded Tomcat web server
        SpringApplication.run(CarServiceApplication.class, args);
    }
}
