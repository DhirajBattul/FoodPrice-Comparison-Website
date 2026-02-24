package com.example.ComparisonService.DTO;

import java.math.BigDecimal;

public record ProductDTO(
		String name,
	    String description,
	    BigDecimal price,
	    String category,
	    Double rating,
	    String restaurantName,
	    String imageUrl) {

}
