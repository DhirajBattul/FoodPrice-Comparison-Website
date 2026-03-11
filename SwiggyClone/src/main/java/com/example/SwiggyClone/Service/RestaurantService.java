package com.example.SwiggyClone.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwiggyClone.Model.Product;
import com.example.SwiggyClone.Model.Restaurant;
import com.example.SwiggyClone.Repo.RestaurantRepo;

@Service
public class RestaurantService {

	@Autowired
    private RestaurantRepo repo;

    public Restaurant saveRestaurant(Restaurant restaurant) {
        // Business logic: Ensure the name isn't null or empty before saving
        
        return repo.save(restaurant);
    }
    
    // NEW
    public List<Restaurant> getAllRestaurants() {
        return repo.findAll();
    }

    // NEW
    public Restaurant getRestaurantById(Integer id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + id));
    }

    // NEW — returns the products list of that restaurant
    public List<Product> getProductsByRestaurant(Integer id) {
        Restaurant restaurant = getRestaurantById(id);
        return restaurant.getProducts();

    }
}
