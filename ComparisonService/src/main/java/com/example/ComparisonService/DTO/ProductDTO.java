package com.example.ComparisonService.DTO;

import java.math.BigDecimal;

public class ProductDTO {
	
	 private Integer id;
	    private String name;
	    private String description;
	    private BigDecimal price;
	    private String category;
	    private Double rating;
	    private String imageUrl;
	    private String restaurantName;

	    public ProductDTO() {}

	    public ProductDTO(Integer id, String name, String description, BigDecimal price,
	                      String category, Double rating, String imageUrl, String restaurantName) {
	        this.id = id;
	        this.name = name;
	        this.description = description;
	        this.price = price;
	        this.category = category;
	        this.rating = rating;
	        this.imageUrl = imageUrl;
	        this.restaurantName = restaurantName;
	    }

	    public Integer getId() { return id; }
	    public void setId(Integer id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getDescription() { return description; }
	    public void setDescription(String description) { this.description = description; }

	    public BigDecimal getPrice() { return price; }
	    public void setPrice(BigDecimal price) { this.price = price; }

	    public String getCategory() { return category; }
	    public void setCategory(String category) { this.category = category; }

	    public Double getRating() { return rating; }
	    public void setRating(Double rating) { this.rating = rating; }

	    public String getImageUrl() { return imageUrl; }
	    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

	    public String getRestaurantName() { return restaurantName; }
	    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

	    @Override
	    public String toString() {
	        return "ProductDTO [id=" + id + ", name=" + name + ", price=" + price +
	               ", category=" + category + ", restaurantName=" + restaurantName + "]";
	    }
}
