package com.devtale.orderservice.service;

import com.devtale.orderservice.dto.OrderDTO;
import com.devtale.orderservice.exception.OrderNotFoundException;
import com.devtale.orderservice.model.Order;
import com.devtale.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getOrderById_Exists_ReturnsDTO() {
        Order order = new Order(1L, 1L, 2, "PENDING",200.00,200.00, LocalDateTime.now());
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDTO result = orderService.getOrderById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getProductId());
        assertEquals("PENDING", result.getStatus());
        verify(orderRepository, times(1)).findById(1L);

    }

    @Test
    void getOrderById_NotFound_ThrowsException(){
        Mockito.when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class,()-> orderService.getOrderById(1L));
        verify(orderRepository,times(1)).findById(1L);
    }

}
