package com.devtale.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="product-service",url="${product-service.url:http://localhost:8081}")
public interface ProductClient {

    @GetMapping("/api/products/{id}")
    Object getProductById(@PathVariable("id") Long id);
}
