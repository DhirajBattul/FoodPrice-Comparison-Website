package com.example.ComparisonService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ComparisonService.DTO.ProductDTO;

@FeignClient(name = "ZOMATO-SERVICE")
public interface ZomatoClient {
	
	@GetMapping("search") 
    ProductDTO searchProduct(@RequestParam("name") String name);
}
