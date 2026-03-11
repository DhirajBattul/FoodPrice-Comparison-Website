package com.example.ComparisonService.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ComparisonService.DTO.CachedPriceDTO;
import com.example.ComparisonService.DTO.ComparisonResponse;
import com.example.ComparisonService.DTO.ProductDTO;
import com.example.ComparisonService.client.SwiggyClient;
import com.example.ComparisonService.client.ZomatoClient;
import com.example.ComparisonService.model.ProductMapping;
import com.example.ComparisonService.repo.ProductMappingRepo;

@Service
public class AggregatorService {

    @Autowired private ZomatoClient zomatoClient;
    @Autowired private SwiggyClient swiggyClient;
    @Autowired private ProductMappingRepo productMappingRepository; 
    @Autowired private PriceFetchService priceFetchService;// NEW

    // ── EXISTING — name-based search ──────────────────────────────────────────
    public List<ComparisonResponse> compare(String query) {
        List<ProductDTO> zResults = new ArrayList<>();
        List<ProductDTO> sResults = new ArrayList<>();

        try {
            zResults = zomatoClient.searchProduct(query);
        } catch (Exception e) {
            System.err.println("Zomato search failed: " + e.getMessage());
        }

        try {
            sResults = swiggyClient.searchProduct(query);
        } catch (Exception e) {
            System.err.println("Swiggy search failed: " + e.getMessage());
        }

        List<ComparisonResponse> responses = new ArrayList<>();

        for (ProductDTO zProd : zResults) {
            ComparisonResponse response = new ComparisonResponse();
            response.setZomatoResult(zProd);

            // Match Swiggy product by both product name AND restaurant name
            sResults.stream()
                .filter(s -> s.getName().equalsIgnoreCase(zProd.getName())
                          && s.getRestaurantName().equalsIgnoreCase(zProd.getRestaurantName()))
                .findFirst()
                .ifPresent(response::setSwiggyResult);

            calculateBestPrice(response);
            responses.add(response);
        }

        // Also add Swiggy-only products that had no Zomato match
        for (ProductDTO sProd : sResults) {
            boolean alreadyMatched = responses.stream()
                .filter(r -> r.getSwiggyResult() != null)
                .anyMatch(r -> r.getSwiggyResult().getName()
                    .equalsIgnoreCase(sProd.getName())
                    && r.getSwiggyResult().getRestaurantName()
                    .equalsIgnoreCase(sProd.getRestaurantName()));

            if (!alreadyMatched) {
                ComparisonResponse response = new ComparisonResponse();
                response.setSwiggyResult(sProd);
                calculateBestPrice(response);
                responses.add(response);
            }
        }

        return responses;
    }

    // ── NEW — ID-based comparison (user clicks a product) ─────────────────────
    public ComparisonResponse compareById(Integer productMappingId) {
        ComparisonResponse response = new ComparisonResponse();

        ProductMapping mapping = productMappingRepository.findById(productMappingId)
                .orElseThrow(() -> new RuntimeException(
                    "Product mapping not found: " + productMappingId));

        try {
            // @Cacheable is on PriceFetchService — proxy works correctly
            // Cache HIT  → returns instantly from Redis, no Feign call
            // Cache MISS → calls ZomatoClone, saves to Redis, returns result
            CachedPriceDTO zomato = priceFetchService
                .fetchZomatoPrice(productMappingId, mapping.getZomatoProductId());

            ProductDTO zomatoDTO = new ProductDTO();
            zomatoDTO.setPrice(zomato.getPrice());
            zomatoDTO.setName(mapping.getCommonName());
            response.setZomatoResult(zomatoDTO);

        } catch (Exception e) {
            System.err.println("Zomato price fetch failed: " + e.getMessage());
        }

        try {
            // Same cache logic for Swiggy — independent from Zomato
            CachedPriceDTO swiggy = priceFetchService
                .fetchSwiggyPrice(productMappingId, mapping.getSwiggyProductId());

            ProductDTO swiggyDTO = new ProductDTO();
            swiggyDTO.setPrice(swiggy.getPrice());
            swiggyDTO.setName(mapping.getCommonName());
            response.setSwiggyResult(swiggyDTO);

        } catch (Exception e) {
            System.err.println("Swiggy price fetch failed: " + e.getMessage());
        }

        calculateBestPrice(response);
        return response;
    }


    // ── EXISTING — shared comparison logic ────────────────────────────────────
    private void calculateBestPrice(ComparisonResponse response) {
        if (response.getZomatoResult() == null && response.getSwiggyResult() == null) {
            response.setMessage("Item not found on either platform.");
            return;
        }

        if (response.getZomatoResult() == null) {
            response.setBestPriceSource("Swiggy");
            return;
        }

        if (response.getSwiggyResult() == null) {
            response.setBestPriceSource("Zomato");
            return;
        }

        BigDecimal zPrice = response.getZomatoResult().getPrice();
        BigDecimal sPrice = response.getSwiggyResult().getPrice();
        int comparison = zPrice.compareTo(sPrice);

        if (comparison < 0) {
            response.setBestPriceSource("Zomato");
            response.setMessage("Zomato is cheaper by ₹" + sPrice.subtract(zPrice));
        } else if (comparison > 0) {
            response.setBestPriceSource("Swiggy");
            response.setMessage("Swiggy is cheaper by ₹" + zPrice.subtract(sPrice));
        } else {
            response.setBestPriceSource("Equal");
            response.setMessage("Both have the same price.");
        }
    }
}


