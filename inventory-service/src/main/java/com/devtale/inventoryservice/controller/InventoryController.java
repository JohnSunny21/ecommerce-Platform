package com.devtale.inventoryservice.controller;


import com.devtale.inventoryservice.dto.InventoryDTO;
import com.devtale.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;


    @GetMapping("/")
    public ResponseEntity<List<InventoryDTO>> getAllInventory(){
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryDTO> getInventoryById(@PathVariable Long id){
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(id));
    }

    @PostMapping
    public ResponseEntity<InventoryDTO> createInventory(@RequestBody InventoryDTO inventoryDTO){
        return new ResponseEntity<>(inventoryService.createInventory(inventoryDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO dto){
        return ResponseEntity.ok(inventoryService.updateInventory(id,dto));
    }

    @DeleteMapping
    public ResponseEntity<InventoryDTO> deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }


}
