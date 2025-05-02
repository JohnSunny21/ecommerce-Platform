package com.devtale.productservice.service;

import com.devtale.productservice.DTO.ProductDTO;
import com.devtale.productservice.exception.ProductNotFoundException;
import com.devtale.productservice.model.Product;
import com.devtale.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProductById_ProductExists_ReturnsProductDTO(){
        Product product = new Product(1L,"Laptop","High-end Laptop",199999.99,"Electronics");
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductDTO result = productService.getProductById(1l);

        assertNotNull(result);
        assertEquals("Laptop",result.getName());
        verify(productRepository,times(1)).findById(1L);
    }

    @Test
    void getProductById_ProductNotFound_ThrowsException(){
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
        verify(productRepository,times(1)).findById(1L);
    }
}
