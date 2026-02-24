package com.example.ZomatoClone.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ZomatoClone.Model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant,Integer>{
	

}
