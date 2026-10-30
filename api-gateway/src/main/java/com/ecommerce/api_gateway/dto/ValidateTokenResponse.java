package com.ecommerce.api_gateway.dto;

import lombok.Data;

@Data
public class ValidateTokenResponse {

    private boolean valid;

    private String username;
}