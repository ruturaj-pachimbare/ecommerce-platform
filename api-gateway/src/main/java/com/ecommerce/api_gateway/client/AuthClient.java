package com.ecommerce.api_gateway.client;

import com.ecommerce.api_gateway.config.FeignConfig;
import com.ecommerce.api_gateway.dto.ValidateTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTH-SERVICE",
        configuration = FeignConfig.class)
public interface AuthClient {

    @GetMapping("/api/auth/validate")
    ValidateTokenResponse validateToken(
            @RequestHeader("Authorization")
            String token);
}