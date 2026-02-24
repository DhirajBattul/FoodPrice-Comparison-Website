package com.example.ComparisonService.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ComparisonService.DTO.ComparisonResponse;
import com.example.ComparisonService.DTO.ProductDTO;
import com.example.ComparisonService.client.SwiggyClient;
import com.example.ComparisonService.client.ZomatoClient;
@Service
public class AggregatorService {
	
	@Autowired private ZomatoClient zomatoClient;
    @Autowired private SwiggyClient swiggyClient;

    public ComparisonResponse compare(String query) {
        ComparisonResponse response = new ComparisonResponse();

        // 1. Fetch Zomato Data (Handle failure gracefully)
        try {
            ProductDTO zData = zomatoClient.searchProduct(query);
            response.setZomatoResult(zData);
        } catch (Exception e) {
            // Log error, but don't crash the whole app
            System.err.println("Zomato is down or item not found: " + e.getMessage());
        }

        // 2. Fetch Swiggy Data
        try {
            ProductDTO sData = swiggyClient.searchProduct(query);
            response.setSwiggyResult(sData);
        } catch (Exception e) {
            System.err.println("Swiggy is down or item not found: " + e.getMessage());
        }

        // 3. Compare Logic
        calculateBestPrice(response);
        
        return response;
    }

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

        BigDecimal zPrice = response.getZomatoResult().price();
        BigDecimal sPrice = response.getSwiggyResult().price();
        
        int comparison = zPrice.compareTo(sPrice);
        
        if (comparison < 0) {
            response.setBestPriceSource("Zomato");
            response.setMessage("Zomato is cheaper by " + sPrice.subtract(zPrice));
        } else if (comparison > 0) {
            response.setBestPriceSource("Swiggy");
            response.setMessage("Swiggy is cheaper by " + zPrice.subtract(sPrice));
        } else {
            response.setBestPriceSource("Equal");
            response.setMessage("Both have the same price.");
        }
    }
}
