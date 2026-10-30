package com.ecommerce.inventory_service.kafka;

import com.ecommerce.inventory_service.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DltConsumer {

    @KafkaListener(
            topics = "order-created-dlt",
            groupId = "dlt-group"
    )
    public void consume(
            OrderCreatedEvent event) {

        log.error(
                "DLT EVENT RECEIVED : {}",
                event);
    }
}