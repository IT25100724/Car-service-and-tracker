package com.example.car_service.customer;

import lombok.Data;

@Data
public class CustomerRegistrationDTO {
    // User fields
    private String username;
    private String password;

    // Customer profile fields
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
}
