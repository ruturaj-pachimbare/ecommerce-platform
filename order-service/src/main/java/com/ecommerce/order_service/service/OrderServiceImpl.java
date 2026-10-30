package com.ecommerce.order_service.service;
import com.ecommerce.order_service.dto.InventoryResponseDto;
import com.ecommerce.order_service.event.OrderCreatedEvent;
import com.ecommerce.order_service.dto.OrderRequestDto;
import com.ecommerce.order_service.dto.OrderResponseDto;
import com.ecommerce.order_service.dto.ProductResponseDto;
import com.ecommerce.order_service.entity.Order;
import com.ecommerce.order_service.entity.OrderStatus;
import com.ecommerce.order_service.exception.OrderNotFoundException;
import com.ecommerce.order_service.feign.InventoryClient;
import com.ecommerce.order_service.feign.ProductClient;
import com.ecommerce.order_service.kafka.OrderEventProducer;
import com.ecommerce.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderEventProducer eventProducer;

    private final OrderRepository repository;

    private final ProductClient productClient;

    private final InventoryClient inventoryClient;

    private static final Logger log =
            LoggerFactory.getLogger(
                    OrderServiceImpl.class);

    @Override
    @Transactional
    @Retry(
            name = "productService"
    )
    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "productFallback"
    )
    public OrderResponseDto createOrder(OrderRequestDto request) {
        log.info(
                "Calling Product Service");

        //validation part
        /*InventoryResponseDto inventory =
                inventoryClient.getInventory(
                        request.getProductId());

        if (inventory.getAvailableQuantity()
                < request.getQuantity()) {

            throw new RuntimeException(
                    "Insufficient stock");
        }
*/

        ProductResponseDto product =
                productClient.getProduct(
                        request.getProductId());

        Order order = new Order();

        order.setProductId(
                request.getProductId());

        order.setQuantity(
                request.getQuantity());

        order.setTotalPrice(
                product.getPrice()
                        .multiply(
                                java.math.BigDecimal
                                        .valueOf(
                                                request.getQuantity())));

        order.setStatus(
                OrderStatus.CREATED);

        order.setCreatedAt(
                LocalDateTime.now());

        order = repository.save(order);

        try {

            eventProducer.publishOrderCreatedEvent(
                    new OrderCreatedEvent(
                            order.getOrderId(),
                            order.getProductId(),
                            order.getQuantity()
                    ));

        }
        catch (Exception ex) {
            log.error("Kafka publish failed", ex);
        }

        return map(order);
    }

    public OrderResponseDto productFallback(
            OrderRequestDto request,
            Exception ex) {

        log.error(
                "Fallback executed",
                ex);

        OrderResponseDto response =
                new OrderResponseDto();

        response.setStatus(
                "PRODUCT SERVICE DOWN");

        return response;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {

        return repository.findAll()
                .stream()
                .map(this::map)
                .toList();
    }

    @Override
    public OrderResponseDto getOrderById(
            Long id) {

        Order order = repository.findById(id)
                .orElseThrow(() ->
                        new OrderNotFoundException(
                                "Order not found"));

        return map(order);
    }

    private OrderResponseDto map(
            Order order) {

        OrderResponseDto dto =
                new OrderResponseDto();

        dto.setOrderId(
                order.getOrderId());

        dto.setProductId(
                order.getProductId());

        dto.setQuantity(
                order.getQuantity());

        dto.setTotalPrice(
                order.getTotalPrice());

        dto.setStatus(
                order.getStatus().name());

        return dto;
    }
}