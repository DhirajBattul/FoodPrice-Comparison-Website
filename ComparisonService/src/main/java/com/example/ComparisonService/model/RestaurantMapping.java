package com.example.ComparisonService.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "restaurant_mapping")
public class RestaurantMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String commonName;

    private String commonLocation;

    

    

    private Integer zomatoRestaurantId;   // ID in ZomatoClone's DB
    private Integer swiggyRestaurantId;   // ID in SwiggyClone's DB

    @OneToMany(mappedBy = "restaurantMapping", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<ProductMapping> products;

    public RestaurantMapping() {}

    public RestaurantMapping(Integer id, String commonName, String commonLocation,
            Integer zomatoRestaurantId, Integer swiggyRestaurantId) {
        this.id = id;
        this.commonName = commonName;
        this.commonLocation = commonLocation;
        
        this.zomatoRestaurantId = zomatoRestaurantId;
        this.swiggyRestaurantId = swiggyRestaurantId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCommonName() { return commonName; }
    public void setCommonName(String commonName) { this.commonName = commonName; }

    public String getCommonLocation() { return commonLocation; }
    public void setCommonLocation(String commonLocation) { this.commonLocation = commonLocation; }

  

    public Integer getZomatoRestaurantId() { return zomatoRestaurantId; }
    public void setZomatoRestaurantId(Integer zomatoRestaurantId) { this.zomatoRestaurantId = zomatoRestaurantId; }

    public Integer getSwiggyRestaurantId() { return swiggyRestaurantId; }
    public void setSwiggyRestaurantId(Integer swiggyRestaurantId) { this.swiggyRestaurantId = swiggyRestaurantId; }

    public List<ProductMapping> getProducts() { return products; }
    public void setProducts(List<ProductMapping> products) { this.products = products; }
}