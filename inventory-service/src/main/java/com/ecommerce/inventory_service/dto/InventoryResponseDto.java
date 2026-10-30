package com.ecommerce.inventory_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {

    private Long productId;

    private Integer availableQuantity;

    private boolean inStock;
}