package com.example.SwiggyClone.Dto;

import java.math.BigDecimal;

import com.example.SwiggyClone.Model.Product;

public record ProductDTO(
    String name,
    String description,
    BigDecimal price,
    String category,
    Double rating,
    String restaurantName,
    String imageUrl
) {
    // Compact Constructor for Entity -> DTO conversion logic
    public ProductDTO(Product product) {
        this(
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getCategory(),
            product.getRating(),
            // Handle null safety for the restaurant relationship
            (product.getRestaurant() != null) ? product.getRestaurant().getName() : null,
            		product.getImageUrl()
        );
    }
}