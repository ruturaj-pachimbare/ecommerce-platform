package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryResponseDto;
import com.ecommerce.inventory_service.entity.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository repository;

    public InventoryResponseDto getInventory(
            Long productId) {
        Inventory inventory =
                repository.findByProductId(productId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Inventory not found"));

        return new InventoryResponseDto(
                inventory.getProductId(),
                inventory.getAvailableQuantity(),
                inventory.getAvailableQuantity() > 0
        );
    }
}
