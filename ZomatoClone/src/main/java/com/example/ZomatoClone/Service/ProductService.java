package com.example.ZomatoClone.Service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ZomatoClone.Dto.ProductDTO;
import com.example.ZomatoClone.Model.Product;
import com.example.ZomatoClone.Repo.ProductRepo;

@Service
public class ProductService {
	
	@Autowired
	ProductRepo repo;
	
	
	public Product addProduct(Product product) {
		return repo.save(product);
	}


	


	public ProductDTO getProductByName(String name) {
		Product product=repo.findByName(name).orElseThrow(() -> new RuntimeException("Product not found"));
		
		return new ProductDTO(product);
	}

}
