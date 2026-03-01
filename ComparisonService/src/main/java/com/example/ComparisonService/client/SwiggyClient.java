package com.example.ComparisonService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.ComparisonService.DTO.ProductDTO;

@FeignClient(name = "SWIGGY-SERVICE")
public interface SwiggyClient {

	@GetMapping("/products/search")
    ProductDTO searchProduct(@RequestParam("name") String name);
}
