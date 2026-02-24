package com.example.SwiggyClone.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
