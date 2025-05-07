package com.devtale.orderservice.service;

import com.devtale.orderservice.client.InventoryClient;
import com.devtale.orderservice.client.ProductClient;
import com.devtale.orderservice.dto.OrderDTO;
import com.devtale.orderservice.exception.OrderNotFoundException;
import com.devtale.orderservice.model.Order;
import com.devtale.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;

    public OrderDTO createOrder(OrderDTO orderDTO){

        // Validate product exists

        Object productResponse = productClient.getProductById(orderDTO.getProductId());
        if(!(productResponse instanceof LinkedHashMap product)){
            throw new RuntimeException("Product not found  : " +orderDTO.getProductId());
        }

        Double price = ((Number) product.get("price")).doubleValue();

        // Validate Stock
        Object inventoryResponse = inventoryClient.getInventoryByProductId(orderDTO.getProductId());
        if(!(inventoryResponse instanceof LinkedHashMap inventory)){
            throw new RuntimeException("Inventory not found for the product : " + orderDTO.getProductId());
        }

        int stockQuantity = (int) inventory.get("stockQuantity");
        if (stockQuantity < orderDTO.getQuantity()){
            throw new RuntimeException("Insufficient stock for product : "+orderDTO.getProductId());
        }

        // Create Order
        Order order = new Order();
        order.setProductId(orderDTO.getProductId());
        order.setQuantity(orderDTO.getQuantity());
        order.setStatus("PENDING");
        order.setPrice(price);
        order.setTotalOrderAmount(price * orderDTO.getQuantity());
        order.setCreateAt(LocalDateTime.now());
        order = orderRepository.save(order);

        // Update inventory
        inventoryClient.updateInventory(orderDTO.getProductId(),stockQuantity - orderDTO.getQuantity());


        // Return DTO
//        orderDTO.setId(order.getId());
//        orderDTO.setProductId(order.getProductId());
//        orderDTO.setStatus(order.getStatus());
//        orderDTO.setPrice(order.getPrice());
//        orderDTO.setTotalOrderAmount(order.getTotalOrderAmount());
//        orderDTO.setCreateAt(order.getCreateAt());
        return convertToDTO(order);
    }

    public OrderDTO getOrderById(Long id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found : " +id));



//        OrderDTO dto = new OrderDTO();
//        dto.setId(order.getId());
//        dto.setProductId(order.getProductId());
//        dto.setQuantity(order.getQuantity());
//        dto.setPrice(order.getPrice());
//        dto.setTotalOrderAmount(order.getTotalOrderAmount());
//        dto.setStatus(order.getStatus());
//        dto.setCreateAt(order.getCreateAt());


        return convertToDTO(order);
    }

    public List<OrderDTO> getAllOrders(){
//        return orderRepository.findAll().stream().map(order -> {
//            OrderDTO dto = new OrderDTO();
//            dto.setId(order.getId());
//            dto.setProductId(order.getProductId());
//            dto.setQuantity(order.getQuantity());
//            dto.setStatus(order.getStatus());
//            dto.setPrice(order.getPrice());
//            dto.setTotalOrderAmount(order.getTotalOrderAmount());
//            dto.setCreateAt(order.getCreateAt());
//            return dto;
//        }).toList();

        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .toList();
    }




    public OrderDTO updateOrder(Long id, String status){
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(()-> new OrderNotFoundException("Order not found with id: " +id));
        existingOrder.setStatus(status);
        Order updatedOrder = orderRepository.save(existingOrder);
        return convertToDTO(updatedOrder);
    }

    public void deleteOrder(Long id){
        if(!orderRepository.existsById(id)){
            throw new OrderNotFoundException("Order not found with id : " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setProductId(order.getProductId());
        dto.setQuantity(order.getQuantity());
        dto.setStatus(order.getStatus());
        dto.setPrice(order.getPrice());
        dto.setTotalOrderAmount(order.getTotalOrderAmount());
        dto.setCreateAt(order.getCreateAt());
        return dto;
    }




// <=================> First phase of the code <================>
//    At beginning stage
//    ========================================================


//    public List<OrderDTO> getAllOrders(){
//        return orderRepository.findAll().stream()
//                .map(this::convertToDTO)
//                .collect(Collectors.toList());
//    }
//
//    public OrderDTO getOrderById(Long id){
//        Order order = orderRepository.findById(id)
//                .orElseThrow(() -> new OrderNotFoundException("Order not found with id : " +id));
//        return convertToDTO(order);
//    }

//    public OrderDTO createOrder(OrderDTO orderDTO){
//        Order order = convertToEntity(orderDTO);
//        order.setStatus("PENDING");
//        order.setCreateAt(LocalDateTime.now());
//        order = orderRepository.save(order);
//        return convertToDTO(order);
//    }

//    private Order convertToEntity(OrderDTO dto){
//        Order order = new Order();
//        order.setProductId(dto.getProductId());
//        order.setQuantity(dto.getQuantity());
//
//        return order;
//    }
}
