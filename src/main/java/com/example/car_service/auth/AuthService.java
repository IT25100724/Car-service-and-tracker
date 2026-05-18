package com.example.car_service.auth;

import java.util.Map;

public interface AuthService {

    Map<String, Object> login(LoginRequest request);

    Map<String, Object> seedAdmin(Map<String, String> requestBody);
}
