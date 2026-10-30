package com.ecommerce.order_service.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    private Long orderId;

    private Long productId;

    private Integer quantity;
}