package com.ecommerce.inventory_service.kafka;
import com.ecommerce.inventory_service.event.OrderCreatedEvent;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final InventoryRepository repository;

    @KafkaListener(
            topics = "order-created",
            groupId = "inventory-group"
    )
    public void consume(
            OrderCreatedEvent event) {

        log.info(
                "Received Event : {}",
                event);

        Inventory inventory =
                repository.findByProductId(
                                event.getProductId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Inventory not found"));

        inventory.setAvailableQuantity(

                inventory.getAvailableQuantity()
                        - event.getQuantity());

        repository.save(inventory);

        log.info(
                "Inventory Updated : {}",
                inventory);
    }
}