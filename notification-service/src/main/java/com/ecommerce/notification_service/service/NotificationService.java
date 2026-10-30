package com.ecommerce.notification_service.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {

    public void sendOrderNotification(
            Long orderId,
            Long productId,
            Integer quantity) {

        log.info(
                "Notification Sent -> OrderId={}, ProductId={}, Quantity={}",
                orderId,
                productId,
                quantity);
    }
}