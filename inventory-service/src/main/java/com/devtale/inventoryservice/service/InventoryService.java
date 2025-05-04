package com.devtale.inventoryservice.service;


import com.devtale.inventoryservice.dto.InventoryDTO;
import com.devtale.inventoryservice.exception.InventoryNotFoundException;
import com.devtale.inventoryservice.model.Inventory;
import com.devtale.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {


    @Autowired
    private InventoryRepository inventoryRepository;

    public List<InventoryDTO> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InventoryDTO getInventoryByProductId(Long productId){
        Inventory inventory = inventoryRepository.findById(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product id : " + productId));
        return convertToDTO(inventory);
    }

    public InventoryDTO createInventory(InventoryDTO dto){
        Inventory inventory = convertToEntity(dto);
        Inventory savedInventory  = inventoryRepository.save(inventory);
        return convertToDTO(savedInventory);
    }

    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO){
        Inventory foundInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product id : " + id));
        foundInventory.setStockQuantity(inventoryDTO.getStockQuantity());
        Inventory updatedInventory = inventoryRepository.save(foundInventory);
        return convertToDTO(updatedInventory);
    }

    public void deleteInventory(Long id){
        if(!inventoryRepository.existsById(id)){
            throw new InventoryNotFoundException("Inventory not found for the product id : "+id);
        }
        inventoryRepository.deleteById(id);
    }

    private InventoryDTO convertToDTO(Inventory inventory){
        InventoryDTO dto = new InventoryDTO();
        dto.setProductId(inventory.getProductId());
        dto.setStockQuantity(inventory.getStockQuantity());
        return dto;
    }

    private Inventory convertToEntity(InventoryDTO dto){
        Inventory inventory = new Inventory();
        inventory.setProductId(dto.getProductId());
        inventory.setStockQuantity(dto.getStockQuantity());
        return inventory;
    }

}
