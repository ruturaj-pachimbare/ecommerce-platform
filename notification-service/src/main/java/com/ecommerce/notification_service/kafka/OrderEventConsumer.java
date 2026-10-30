package com.ecommerce.notification_service.kafka;

import com.ecommerce.notification_service.event.OrderCreatedEvent;
import com.ecommerce.notification_service.service.NotificationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final NotificationService notificationService;

    @PostConstruct
    public void init() {
        log.info("Kafka Consumer Bean Loaded");
    }

    @KafkaListener(
            topics = "order-created",
            groupId = "notification-group"
    )
    public void consume(
            OrderCreatedEvent event) {

        log.info(
                "Notification Event Received : {}",
                event);

        notificationService.sendOrderNotification(
                event.getOrderId(),
                event.getProductId(),
                event.getQuantity()
        );
    }
}