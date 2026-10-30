package com.ecommerce.order_service.kafka;

import com.ecommerce.order_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventProducer {

    private final KafkaTemplate<String,Object>
            kafkaTemplate;

   /* public void publishOrderCreatedEvent(
            OrderCreatedEvent event) {

        kafkaTemplate.send(
                "order-created",
                event);

        log.info(
                "Order Created Event Published : {}",
                event);
    }*/

    public void publishOrderCreatedEvent(OrderCreatedEvent event) {
        kafkaTemplate.send("order-created", event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Order Created Event Published successfully to offset: {}",
                                result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to publish Order Created Event to Kafka: {}",
                                ex.getMessage());
                        // Handle compensating logic here if needed (e.g., changing order status to FAILED)
                    }
                });
    }
}