package com.example.ComparisonService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ComparisonService.DTO.ComparisonResponse;
import com.example.ComparisonService.model.ProductMapping;
import com.example.ComparisonService.model.RestaurantMapping;
import com.example.ComparisonService.repo.ProductMappingRepo;
import com.example.ComparisonService.repo.RestaurantMappingRepo;
import com.example.ComparisonService.service.AggregatorService;
import com.example.ComparisonService.service.SeedService;

@RestController



public class ComparisonController {
	@Autowired
	private AggregatorService aggregatorService;
	@Autowired 
	private RestaurantMappingRepo restaurantMappingRepository;
    @Autowired 
    private ProductMappingRepo productMappingRepository;
    @Autowired
    private SeedService seedService;
	
	@GetMapping("/compare/{foodName}")
    public ResponseEntity<List<ComparisonResponse>> comparePrice(@PathVariable String foodName) {
        return ResponseEntity.ok(aggregatorService.compare(foodName));
    }
	
	// ── NEW — user clicks a product → live price comparison ───────────────────
    @GetMapping("/compare/product/{productMappingId}")
    public ResponseEntity<ComparisonResponse> compareById(@PathVariable Integer productMappingId) {
        return ResponseEntity.ok(aggregatorService.compareById(productMappingId));
    }

    // ── NEW — list all restaurants (from ComparisonService DB) ────────────────
    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantMapping>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantMappingRepository.findAll());
    }

    // ── NEW — list all products for a restaurant ──────────────────────────────
    @GetMapping("/restaurants/{restaurantMappingId}/products")
    public ResponseEntity<List<ProductMapping>> getProductsByRestaurant(
            @PathVariable Integer restaurantMappingId) {
        return ResponseEntity.ok(
            productMappingRepository.findByRestaurantMapping_Id(restaurantMappingId));
    }

    // ── NEW — seed data from both clones into ComparisonService DB ────────────
    @PostMapping("/admin/seed")
    public ResponseEntity<String> seed() {
        seedService.seedData();
        return ResponseEntity.ok("Seeding complete");
    }
}
