package com.ecommerce.auth_service.service;

import com.ecommerce.auth_service.dto.LoginRequest;
import com.ecommerce.auth_service.dto.RegisterRequest;
import com.ecommerce.auth_service.dto.ValidateTokenResponse;

public interface AuthService {

    void register(
            RegisterRequest request);

    String  login(
            LoginRequest request);

    ValidateTokenResponse validateToken(String token);
}