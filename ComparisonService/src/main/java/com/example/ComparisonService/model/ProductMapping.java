package com.example.ComparisonService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

@Entity
@Table(name = "product_mapping")
public class ProductMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_mapping_id", nullable = false)
    @JsonBackReference
    private RestaurantMapping restaurantMapping;

    @Column(nullable = false)
    private String commonName;

    @Column(length = 1000)
    private String commonImageUrl;

    private String category;

    @Column(nullable = false)
    private Integer zomatoProductId;

    @Column(nullable = false)
    private Integer swiggyProductId;

    public ProductMapping() {}

    public ProductMapping(Integer id, RestaurantMapping restaurantMapping, String commonName,
            String commonImageUrl, String category,
            Integer zomatoProductId, Integer swiggyProductId) {
        this.id = id;
        this.restaurantMapping = restaurantMapping;
        this.commonName = commonName;
        this.commonImageUrl = commonImageUrl;
        this.category = category;
        this.zomatoProductId = zomatoProductId;
        this.swiggyProductId = swiggyProductId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public RestaurantMapping getRestaurantMapping() { return restaurantMapping; }
    public void setRestaurantMapping(RestaurantMapping restaurantMapping) {
        this.restaurantMapping = restaurantMapping;
    }

    public String getCommonName() { return commonName; }
    public void setCommonName(String commonName) { this.commonName = commonName; }

    public String getCommonImageUrl() { return commonImageUrl; }
    public void setCommonImageUrl(String commonImageUrl) { this.commonImageUrl = commonImageUrl; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getZomatoProductId() { return zomatoProductId; }
    public void setZomatoProductId(Integer zomatoProductId) { this.zomatoProductId = zomatoProductId; }

    public Integer getSwiggyProductId() { return swiggyProductId; }
    public void setSwiggyProductId(Integer swiggyProductId) { this.swiggyProductId = swiggyProductId; }

    @Override
    public String toString() {
        return "ProductMapping [id=" + id + ", commonName=" + commonName +
               ", category=" + category + ", zomatoProductId=" + zomatoProductId +
               ", swiggyProductId=" + swiggyProductId + "]";
    }
}