package com.ecommerce.order_service.service;


import com.ecommerce.order_service.dto.OrderRequestDto;
import com.ecommerce.order_service.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {

    OrderResponseDto createOrder(
            OrderRequestDto request);

    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(
            Long id);
}
