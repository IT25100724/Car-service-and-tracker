package com.example.car_service.customer;

import lombok.Data;

@Data
public class CustomerProfileDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String currentPassword;
    private String newPassword;
}
