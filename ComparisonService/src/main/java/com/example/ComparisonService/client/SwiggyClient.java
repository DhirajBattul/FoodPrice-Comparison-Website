package com.example.ComparisonService.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ComparisonService.DTO.ProductDTO;
import com.example.ComparisonService.DTO.RestaurantDTO;

@FeignClient(name = "SWIGGY-SERVICE")
public interface SwiggyClient {

	// GET /restaurants — fetch all restaurants for seeding/listing
    @GetMapping("/restaurants")
    List<RestaurantDTO> getAllRestaurants();

    // GET /restaurants/{id}/products — fetch products of a restaurant
    @GetMapping("/restaurants/{id}/products")
    List<ProductDTO> getProductsByRestaurant(@PathVariable("id") Integer restaurantId);

    // GET /products/{id}/price — fetch live price of a single product (used in comparison)
    @GetMapping("/products/{id}/price")
    ProductDTO getProductPrice(@PathVariable("id") Integer productId);

    // GET /products/search?name= — keep existing for name-based search feature
    @GetMapping("/products/search")
    List<ProductDTO> searchProduct(@RequestParam("name") String name);
}
