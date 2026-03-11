package com.example.ZomatoClone.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ZomatoClone.Model.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
	
	Optional<Product> findByName(String name);
	
	Optional<Product> findByNameIgnoreCase(String name);
	
	 List<Product> findByNameContainingIgnoreCase(String name);

}

