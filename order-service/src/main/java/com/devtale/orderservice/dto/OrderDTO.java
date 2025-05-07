package com.devtale.orderservice.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderDTO {

    private Long id;
    private Long productId;
    private Integer quantity;
    private String status;
    private Double price;
    private Double totalOrderAmount;
    private LocalDateTime createAt;
}
