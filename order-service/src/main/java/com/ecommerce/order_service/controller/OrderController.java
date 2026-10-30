package com.ecommerce.order_service.controller;
import com.ecommerce.order_service.dto.OrderRequestDto;
import com.ecommerce.order_service.dto.OrderResponseDto;
import com.ecommerce.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping
    public OrderResponseDto createOrder(
            @RequestBody
            @Valid
            OrderRequestDto request) {

        return service.createOrder(request);
    }

    @GetMapping
    public List<OrderResponseDto> getAllOrders() {

        return service.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrder(
            @PathVariable Long id) {

        return service.getOrderById(id);
    }
}