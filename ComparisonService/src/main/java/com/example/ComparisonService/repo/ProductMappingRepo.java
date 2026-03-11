package com.example.ComparisonService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ComparisonService.model.ProductMapping;

@Repository
public interface ProductMappingRepo extends JpaRepository<ProductMapping, Integer> {
	
	   List<ProductMapping> findByRestaurantMapping_Id(Integer restaurantMappingId);

	    // Find by name within a restaurant
	    Optional<ProductMapping> findByCommonNameIgnoreCaseAndRestaurantMapping_Id(
	            String commonName, Integer restaurantMappingId);

	    // Find by category within a restaurant (e.g. "Starters", "Drinks")
	    List<ProductMapping> findByRestaurantMapping_IdAndCategoryIgnoreCase(
	            Integer restaurantMappingId, String category);

	    // Check if zomato/swiggy product is already mapped
	    boolean existsByZomatoProductId(Integer zomatoProductId);
	    boolean existsBySwiggyProductId(Integer swiggyProductId);

}
