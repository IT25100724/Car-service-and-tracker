package com.example.car_service.auth;

import lombok.Data;


@Data
public class LoginRequest {

    // Encapsulation
    private String email;
    private String password;
    private String role;
}
