package com.devtale.productservice.service;


import com.devtale.productservice.dto.ProductDTO;
import com.devtale.productservice.exception.ProductNotFoundException;
import com.devtale.productservice.model.Product;
import com.devtale.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts(){
        return productRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public ProductDTO getProductById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: "+id));
        return convertToDTO(product);
    }

    public ProductDTO createProduct(ProductDTO productDTO){
        Product product = convertToEntity(productDTO);
        product = productRepository.save(product);
        return convertToDTO(product);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO){
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(()-> new ProductNotFoundException("Product not found with id: " +id));
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(productDTO.getCategory());
        existingProduct.setPrice(productDTO.getPrice());
        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    public void deleteProduct(Long id){
        if (!productRepository.existsById(id)){
            throw new ProductNotFoundException("Product not found with id: " +id);
        }
        productRepository.deleteById(id);
    }


    private ProductDTO convertToDTO(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        return dto;
    }

    private Product convertToEntity(ProductDTO dto){
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(dto.getCategory());
        return product;
    }
}
