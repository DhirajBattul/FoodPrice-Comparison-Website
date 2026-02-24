package com.example.SwiggyClone.Repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SwiggyClone.Model.Restaurant;

public interface RestaurantRepo extends JpaRepository<Restaurant,Integer>{
	

}
