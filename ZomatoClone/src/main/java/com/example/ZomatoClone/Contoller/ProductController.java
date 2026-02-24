package com.example.ZomatoClone.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ZomatoClone.Dto.ProductDTO;
import com.example.ZomatoClone.Model.Product;
import com.example.ZomatoClone.Service.ProductService;



@RestController
public class ProductController {
	
	@Autowired
	ProductService service;
	
	
	@PostMapping("product")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		
		return ResponseEntity.ok(service.addProduct(product));
	}
	
	@GetMapping("search")
    public ResponseEntity<ProductDTO> searchProduct(@RequestParam String name) {
        
        return ResponseEntity.ok(service.getProductByName(name));
    }
	
	

}
