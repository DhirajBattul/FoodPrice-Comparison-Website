package com.example.ComparisonService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ComparisonService.DTO.ProductDTO;
import com.example.ComparisonService.DTO.RestaurantDTO;
import com.example.ComparisonService.client.SwiggyClient;
import com.example.ComparisonService.client.ZomatoClient;
import com.example.ComparisonService.model.ProductMapping;
import com.example.ComparisonService.model.RestaurantMapping;
import com.example.ComparisonService.repo.ProductMappingRepo;
import com.example.ComparisonService.repo.RestaurantMappingRepo;

@Service
public class SeedService {

    @Autowired private ZomatoClient zomatoClient;
    @Autowired private SwiggyClient swiggyClient;
    @Autowired private RestaurantMappingRepo restaurantMappingRepository;
    @Autowired private ProductMappingRepo productMappingRepository;

    public void seedData() {

        // 1. Fetch all restaurants from both clones
        List<RestaurantDTO> zomatoRestaurants = zomatoClient.getAllRestaurants();
        List<RestaurantDTO> swiggyRestaurants = swiggyClient.getAllRestaurants();

        if (zomatoRestaurants == null || swiggyRestaurants == null) {
            System.err.println("Failed to fetch restaurants from one or both clones.");
            return;
        }

        // 2. Match restaurants by name across both clones
        for (RestaurantDTO zRest : zomatoRestaurants) {

            swiggyRestaurants.stream()
                .filter(s -> s.getName().equalsIgnoreCase(zRest.getName()))
                .findFirst()
                .ifPresent(sRest -> {

                    // Skip if this zomato restaurant is already mapped
                    if (restaurantMappingRepository.existsByZomatoRestaurantId(zRest.getId())) {
                        System.out.println("Already seeded restaurant: " + zRest.getName() + " — skipping.");
                        return;
                    }

                    // 3. Save RestaurantMapping
                    RestaurantMapping restaurantMapping = new RestaurantMapping();
                    restaurantMapping.setCommonName(zRest.getName());
                    restaurantMapping.setCommonLocation(zRest.getLocation());
                    restaurantMapping.setZomatoRestaurantId(zRest.getId());
                    restaurantMapping.setSwiggyRestaurantId(sRest.getId());
                    RestaurantMapping savedRestaurant = restaurantMappingRepository.save(restaurantMapping);

                    System.out.println("Seeded restaurant: " + savedRestaurant.getCommonName());

                    // 4. Fetch products for this restaurant from both clones
                    List<ProductDTO> zProducts = zomatoClient.getProductsByRestaurant(zRest.getId());
                    List<ProductDTO> sProducts = swiggyClient.getProductsByRestaurant(sRest.getId());

                    if (zProducts == null || sProducts == null) {
                        System.err.println("Failed to fetch products for: " + zRest.getName());
                        return;
                    }

                    // 5. Match products by name across both clones
                    for (ProductDTO zProd : zProducts) {

                        sProducts.stream()
                            .filter(s -> s.getName().equalsIgnoreCase(zProd.getName()))
                            .findFirst()
                            .ifPresent(sProd -> {

                                // Skip if this zomato product is already mapped
                                if (productMappingRepository.existsByZomatoProductId(zProd.getId())) {
                                    System.out.println("Already seeded product: " + zProd.getName() + " — skipping.");
                                    return;
                                }

                                // 6. Save ProductMapping
                                ProductMapping productMapping = new ProductMapping();
                                productMapping.setRestaurantMapping(savedRestaurant);
                                productMapping.setCommonName(zProd.getName());
                                productMapping.setCommonImageUrl(zProd.getImageUrl());
                                productMapping.setCategory(zProd.getCategory());
                                productMapping.setZomatoProductId(zProd.getId());
                                productMapping.setSwiggyProductId(sProd.getId());
                                productMappingRepository.save(productMapping);

                                System.out.println("Seeded product: " + zProd.getName());
                            });
                    }
                });
        }

        System.out.println("Seeding complete.");
    }
}
