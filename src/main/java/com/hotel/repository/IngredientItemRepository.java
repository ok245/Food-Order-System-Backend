package com.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.JpaRepositoryConfigExtension;

import com.hotel.model.IngredientCategory;
import com.hotel.model.IngredientsItem;

public interface IngredientItemRepository extends JpaRepository<IngredientsItem,Long>{
	List<IngredientsItem>findByRestaurantId(Long id);
}
