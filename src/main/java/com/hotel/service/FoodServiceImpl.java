package com.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.model.Category;
import com.hotel.model.Food;
import com.hotel.model.Restaurant;
import com.hotel.repository.FoodRepository;
import com.hotel.request.CreateFoodRequest;
@Service
public class FoodServiceImpl implements FoodService {

	
	@Autowired
	private FoodRepository foodRepository;
	
	
	@Override
	public Food createFood(CreateFoodRequest req,Category category, Restaurant restaurant) {
		// TODO Auto-generated method stub
		
		Food food=new Food();
		
		food.setFoodCategory(category);
		food.setRestaurant(restaurant);
		food.setDiscription(req.getDiscription());
		food.setImages(req.getImages());
		food.setName(req.getName());
		food.setPrice(req.getPrice());
		food.setIngredients(req.getIngredients());
		food.setSeasonal(req.isSeasonal());
		food.setVegeterian(req.isVegeterian());
		
		 Food savedFood=foodRepository.save(food);
		 restaurant.getFood().add(savedFood);
		 return savedFood;
	}

	@Override
	public void deleteFood(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		Food food=findFoodById(foodId);
	    food.setRestaurant(null);
	    foodRepository.save(food);
		

	}

	@Override
	public List<Food> getRestaurantsFoods(Long restaurantId, boolean isvegetarian, boolean isNonVeg, boolean isSeasonal,
			String foodCategory) {
		// TODO Auto-generated method stub
		List<Food> foods=foodRepository.findByrestaurantId(restaurantId);
		if(isvegetarian) {
			foods=filterByVegeterian(foods,isvegetarian);
		}
		
		if(isNonVeg) {
			foods=filterByNonVeg(foods,isNonVeg);
		}
		
		if(isSeasonal){
			foods=filterBySeasonal(foods,isSeasonal);
		}
		
		
		if(foodCategory!=null && !foodCategory.equals("")) {
			
			foods=fiterByCategory(foods,foodCategory);
		}
		
		
		return foods;
	}

	

	

	private List<Food> fiterByCategory(List<Food> foods, String foodCategory) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food->{
			if(food.getFoodCategory()!=null) {
				return food.getFoodCategory().getName().equals(foodCategory);
			}
			return false;
			
		}).collect(Collectors.toList());
	}

	private List<Food> filterBySeasonal(List<Food> foods, boolean isSeasonal) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food->food.isSeasonal()==isSeasonal).collect(Collectors.toList());
	}

	private List<Food> filterByNonVeg(List<Food> foods, boolean isNonVeg) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food->food.isVegeterian()==false).collect(Collectors.toList());
	}

	private List<Food> filterByVegeterian(List<Food> foods, boolean isvegetarian) {
		// TODO Auto-generated method stub
		return foods.stream().filter(food->food.isVegeterian()==isvegetarian).collect(Collectors.toList());
	}

	@Override
	public List<Food> searchFood(String keyword) {
		// TODO Auto-generated method stub
		return foodRepository.searchFood(keyword);
		
	}

	@Override
	public Food findFoodById(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		Optional<Food> optionalFood=foodRepository.findById(foodId);
		if(optionalFood.isEmpty()) {
			throw new Exception("Food is not exist");
		}
		
		return optionalFood.get();
}

	@Override
	public Food updateFoodAvailibilityStatus(Long foodId) throws Exception {
		// TODO Auto-generated method stub
		Food food=findFoodById(foodId);
		food.setAvailable(!food.isAvailable());
		return  foodRepository.save(food);
		
	}

}
