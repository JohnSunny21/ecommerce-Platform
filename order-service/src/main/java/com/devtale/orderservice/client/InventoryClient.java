package com.devtale.orderservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="inventory-service",url="${inventory-service.url:http://localhost:8082}")
public interface InventoryClient {

    @GetMapping("/api/inventory/{productId}")
    Object getInventoryByProductId(@PathVariable("productId") Long productId);

    @PutMapping("/api/inventory/{productId}")
    void updateInventory(@PathVariable("productId") Long productId, @RequestParam("quantity") int quantity);
}
