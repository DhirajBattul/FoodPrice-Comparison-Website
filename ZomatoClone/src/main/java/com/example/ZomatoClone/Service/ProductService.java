package com.example.ZomatoClone.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ZomatoClone.Dto.ProductDTO;
import com.example.ZomatoClone.Model.Product;
import com.example.ZomatoClone.Repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;
	
	
    public Product addProduct(Product product) {
        return repo.save(product);
    }

    // Existing
    public ProductDTO getProductByName(String name) {
        Product product = repo.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Product not found: " + name));
        return mapToDTO(product);
    }

    // NEW — used by GET /products/{id}/price
    public ProductDTO getProductById(Integer id) {
        Product product = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        return mapToDTO(product);
    }
    
    public List<ProductDTO> getProductsByName(String name) {
        List<Product> products = repo.findByNameContainingIgnoreCase(name);
        if (products.isEmpty()) {
            throw new RuntimeException("No products found matching: " + name);
        }
        return products.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Shared mapping method
    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCategory(product.getCategory());
        dto.setRating(product.getRating());
        dto.setImageUrl(product.getImageUrl());
        dto.setRestaurantName(product.getRestaurantName());
        return dto;
    }

}
