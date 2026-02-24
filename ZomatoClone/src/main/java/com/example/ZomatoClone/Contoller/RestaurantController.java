package com.example.ZomatoClone.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ZomatoClone.Model.Restaurant;
import com.example.ZomatoClone.Service.RestaurantService;

@RestController
public class RestaurantController {
	
	@Autowired
    private RestaurantService restaurantService;

    @PostMapping("restaurant")
    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        Restaurant savedRestaurant = restaurantService.saveRestaurant(restaurant);
        return new ResponseEntity<>(savedRestaurant, HttpStatus.CREATED);
    }
}
