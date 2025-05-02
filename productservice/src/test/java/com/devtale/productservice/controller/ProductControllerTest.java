package com.devtale.productservice.controller;


import com.devtale.productservice.DTO.ProductDTO;
import com.devtale.productservice.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getProductById_ReturnsProductDTO() throws Exception{
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setName("laptop");
        productDTO.setPrice(100000.00);
        productDTO.setDescription("The most high end laptop");
        productDTO.setCategory("Electronics");

        Mockito.when(productService.getProductById(1L)).thenReturn(productDTO);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("laptop"))
                .andExpect(jsonPath("$.price").value(100000.00));
    }
}
