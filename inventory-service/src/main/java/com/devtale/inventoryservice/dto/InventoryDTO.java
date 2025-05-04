package com.devtale.inventoryservice.dto;


import lombok.Data;

@Data
public class InventoryDTO {

    private Long productId;
    private Integer stockQuantity;
}
