package com.example.SwiggyClone.Contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.SwiggyClone.Dto.ProductDTO;
import com.example.SwiggyClone.Model.Product;
import com.example.SwiggyClone.Service.ProductService;



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
