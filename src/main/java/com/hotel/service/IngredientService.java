package com.hotel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.hotel.model.IngredientCategory;
import com.hotel.model.IngredientsItem;

public interface IngredientService {
	
	@Autowired
//	private Ingredient
	
	public IngredientCategory createIngredientCategory(String name,Long restaurantId)throws Exception;
	
	public IngredientCategory findIngredientCategoryById(Long id)throws Exception;
	
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id)throws Exception;
	
	public IngredientsItem createIngredientItem(Long restaurantId,String ingredientname,Long categoryId)throws Exception;
	
    public List<IngredientsItem> findRestaurantIngredients(Long restaurantId);
    
    public IngredientsItem updateStock(Long Id)throws Exception ;
}
