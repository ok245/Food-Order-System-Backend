package com.hotel.service;

import java.util.List;

import com.hotel.model.Category;
import com.hotel.model.Food;
import com.hotel.model.Restaurant;
import com.hotel.request.CreateFoodRequest;

public interface FoodService {
	
	
	
	public Food createFood(CreateFoodRequest req,Category category,Restaurant restaurant );
	
	public void deleteFood(Long foodId)throws Exception;
	
	public List<Food>  getRestaurantsFoods(Long restaurantId,boolean isvegetarian , boolean isNonVeg, boolean seasonal,String foodCategory);
	
	public List<Food>  searchFood(String keyword);
	
	public Food findFoodById(Long foodId)throws Exception;
	
	public Food updateFoodAvailibilityStatus(Long foodId)throws Exception;

}
