package com.devtale.productservice.DTO;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
}
