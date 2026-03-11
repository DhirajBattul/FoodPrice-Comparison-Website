package com.example.ComparisonService.service;

import com.example.ComparisonService.DTO.CachedPriceDTO;
import com.example.ComparisonService.DTO.ProductDTO;
import com.example.ComparisonService.client.ZomatoClient;
import com.example.ComparisonService.client.SwiggyClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PriceFetchService {

    private final ZomatoClient zomatoClient;
    private final SwiggyClient swiggyClient;

    public PriceFetchService(ZomatoClient zomatoClient, SwiggyClient swiggyClient) {
        this.zomatoClient = zomatoClient;
        this.swiggyClient = swiggyClient;
    }

    // Cache key: "price-zomato::1", "price-zomato::2", etc.
    // Redis auto-evicts after TTL — no manual cleanup needed
    @Cacheable(value = "price-zomato", key = "#productMappingId")
    public CachedPriceDTO fetchZomatoPrice(Integer productMappingId, Integer zomatoProductId) {
        System.out.println("Cache MISS — fetching Zomato price for productMappingId: " + productMappingId);
        ProductDTO result = zomatoClient.getProductPrice(zomatoProductId);
        return new CachedPriceDTO(productMappingId, "ZOMATO", result.getPrice(), true);
    }

    // Cache key: "price-swiggy::1", "price-swiggy::2", etc.
    @Cacheable(value = "price-swiggy", key = "#productMappingId")
    public CachedPriceDTO fetchSwiggyPrice(Integer productMappingId, Integer swiggyProductId) {
        System.out.println("Cache MISS — fetching Swiggy price for productMappingId: " + productMappingId);
        ProductDTO result = swiggyClient.getProductPrice(swiggyProductId);
        return new CachedPriceDTO(productMappingId, "SWIGGY", result.getPrice(), true);
    }

    // Evict both platforms when price is known to have changed
    @CacheEvict(value = {"price-zomato", "price-swiggy"}, key = "#productMappingId")
    public void evictPriceCache(Integer productMappingId) {
        System.out.println("Cache EVICTED for productMappingId: " + productMappingId);
    }
}