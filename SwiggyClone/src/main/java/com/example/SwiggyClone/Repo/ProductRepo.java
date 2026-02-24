package com.example.SwiggyClone.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SwiggyClone.Model.Product;

public interface ProductRepo extends JpaRepository<Product,Integer> {
	
	Optional<Product> findByName(String name);

}

