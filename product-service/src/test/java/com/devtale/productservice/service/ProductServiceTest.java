package com.devtale.productservice.service;

import com.devtale.productservice.client.InventoryClient;
import com.devtale.productservice.dto.ProductDTO;
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
import static org.mockito.Mockito.*;


public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private InventoryClient inventoryClient;

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

    @Test
    void getProductById_ProductExists_ReturnsProductDTOWithStock(){
        Product product = new Product(1L,"Laptop","High-end laptop", 99999.00,"Electronics");
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(inventoryClient.getStockQuantity(1L)).thenReturn(100);

        ProductDTO result = productService.getProductById(1L);

        assertNotNull(result);
        assertEquals("Laptop",result.getName());
        assertEquals(100,result.getStockQuantity());
        verify(productRepository,times(1)).findById(1L);
        verify(inventoryClient,times(1)).getStockQuantity(1L);
    }

    @Test
    void getProductById_ProductNotFound_ThrowsException_NoCallToInventory(){
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,()-> productService.getProductById(1L));
        verify(productRepository,times(1)).findById(1L);
        verify(inventoryClient,never()).getStockQuantity(anyLong());
    }
}
