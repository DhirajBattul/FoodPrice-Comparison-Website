package com.example.SwiggyClone.Service;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.SwiggyClone.Dto.ProductDTO;
import com.example.SwiggyClone.Model.Product;
import com.example.SwiggyClone.Repo.ProductRepo;

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
