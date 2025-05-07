package com.devtale.orderservice.controller;


import com.devtale.orderservice.dto.OrderDTO;
import com.devtale.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDateTime;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;


    @Test
    void getOrderById_RetunsOrderDTO() throws Exception {
        OrderDTO dto = new OrderDTO();
        dto.setId(1L);
        dto.setProductId(1L);
        dto.setQuantity(2);
        dto.setStatus("PENDING");
        dto.setPrice(200.00);
        dto.setTotalOrderAmount(200.00);
        dto.setCreateAt(LocalDateTime.now());

        when(orderService.getOrderById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/orders/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.price").value(200.00))
                .andExpect(jsonPath("$.totalOrderAmount").value(200.00));
    }

    @Test
    void createOrder_ReturnsCreatedOrder() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setProductId(1L);
        orderDTO.setQuantity(2);


        OrderDTO createdDTO = new OrderDTO();
        createdDTO.setId(1L);
        createdDTO.setProductId(1L);
        createdDTO.setQuantity(2);
        createdDTO.setStatus("PENDING");
        createdDTO.setPrice(200.00);
        createdDTO.setTotalOrderAmount(createdDTO.getPrice() * createdDTO.getQuantity());
        createdDTO.setCreateAt(LocalDateTime.now());

        when(orderService.createOrder(orderDTO)).thenReturn(createdDTO);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"productId\":1,\"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.status").value("PENDING"));

    }
}
