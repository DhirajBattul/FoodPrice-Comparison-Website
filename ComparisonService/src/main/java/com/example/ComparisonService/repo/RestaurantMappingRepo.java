package com.example.ComparisonService.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ComparisonService.model.RestaurantMapping;

@Repository
public interface RestaurantMappingRepo extends JpaRepository<RestaurantMapping, Integer> {
	
	Optional<RestaurantMapping> findByCommonNameIgnoreCase(String commonName);

    List<RestaurantMapping> findByCommonLocationContainingIgnoreCase(String location);

    boolean existsByZomatoRestaurantId(Integer zomatoRestaurantId);
    boolean existsBySwiggyRestaurantId(Integer swiggyRestaurantId);

}
