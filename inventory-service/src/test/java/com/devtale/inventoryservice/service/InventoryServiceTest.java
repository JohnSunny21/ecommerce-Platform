package com.devtale.inventoryservice.service;

import com.devtale.inventoryservice.dto.InventoryDTO;
import com.devtale.inventoryservice.exception.InventoryNotFoundException;
import com.devtale.inventoryservice.model.Inventory;
import com.devtale.inventoryservice.repository.InventoryRepository;
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

public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getInventoryByProductId_Exists_ReturnsDTO(){
        Inventory inventory = new Inventory(1L,100);
        Mockito.when(inventoryRepository.findById(1L)).thenReturn(Optional.of(inventory));

        InventoryDTO dto = inventoryService.getInventoryByProductId(1L);
        assertNotNull(dto);
        assertEquals(100,dto.getStockQuantity());
        verify(inventoryRepository,times(1)).findById(1L);
    }

    @Test
    void getInventoryByProductId_NotFound_ThrowsException(){
        Mockito.when(inventoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(InventoryNotFoundException.class, ()-> inventoryService.getInventoryByProductId(1L));
        verify(inventoryRepository, times(1)).findById(1L);
    }
}
