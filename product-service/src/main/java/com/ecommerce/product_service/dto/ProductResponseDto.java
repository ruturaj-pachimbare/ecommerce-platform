package com.ecommerce.product_service.dto;

import java.math.BigDecimal;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {

    private Long productId;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    private String category;

    private String status;
}