package com.devtale.productservice.client;




import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {


    private final RestTemplate restTemplate;

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Integer getStockQuantity(Long productId) {
        String url = "http://inventory-service/api/inventory/" + productId;
        try {
            return  restTemplate.getForObject(url + "?fields=stockQuantity",Integer.class);
        } catch (Exception ex) {
            return 0; // Fall back if inventory-service is unavailable
        }
    }
}
