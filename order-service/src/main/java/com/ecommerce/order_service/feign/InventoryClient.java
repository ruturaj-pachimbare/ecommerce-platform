package com.ecommerce.order_service.feign;

import com.ecommerce.order_service.dto.InventoryResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "INVENTORY-SERVICE"
)
public interface InventoryClient {

    @GetMapping(
            "/api/inventory/{productId}"
    )
    InventoryResponseDto getInventory(
            @PathVariable Long productId);
}