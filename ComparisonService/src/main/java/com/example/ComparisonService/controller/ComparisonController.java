package com.example.ComparisonService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ComparisonService.DTO.ComparisonResponse;
import com.example.ComparisonService.service.AggregatorService;

@RestController
@RequestMapping("/compare")
@CrossOrigin(origins= "http://localhost:3000")

public class ComparisonController {
	@Autowired
	private AggregatorService aggregatorService;
	
	@GetMapping("/{foodName}")
    public ResponseEntity<ComparisonResponse> comparePrice(@PathVariable String foodName) {
        return ResponseEntity.ok(aggregatorService.compare(foodName));
    }
}
