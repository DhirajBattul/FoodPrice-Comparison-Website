package com.example.ZomatoClone.Model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "product", uniqueConstraints = {
	    @UniqueConstraint(columnNames = {"name", "restaurant_id"})
	})
public class Product {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	
    private String name;

    
    private String description;

    
    private BigDecimal price;

    

    private String category;
    
    

    private Double rating;
    
    @Column(length = 1000) 
    private String imageUrl;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonBackReference
    private Restaurant restaurant;



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public Integer getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public BigDecimal getPrice() {
		return price;
	}



	public void setPrice(BigDecimal price) {
		this.price = price;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public Double getRating() {
		return rating;
	}



	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public Restaurant getRestaurant() {
	    return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
	    this.restaurant = restaurant;
	}
	
	@JsonProperty("restaurantName")
	public String getRestaurantName() {
	    return restaurant != null ? restaurant.getName() : null;
	}



	// Add 'Restaurant restaurant' to the parameters




	public Product() {
		super();
	}



	public Product(Integer id, String name, String description, BigDecimal price, String category, Double rating,
			String imageUrl, Restaurant restaurant) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.category = category;
		this.rating = rating;
		this.imageUrl = imageUrl;
		this.restaurant = restaurant;
	}



	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", category=" + category + ", rating=" + rating + ", imageUrl=" + imageUrl + ", restaurant="
				+ restaurant + "]";
	}



	
	
    

}
