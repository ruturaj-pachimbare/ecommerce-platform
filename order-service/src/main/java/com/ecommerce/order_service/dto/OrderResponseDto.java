package com.ecommerce.order_service.dto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderResponseDto {

    private Long orderId;

    private Long productId;

    private Integer quantity;

    private BigDecimal totalPrice;

    private String status;
}
