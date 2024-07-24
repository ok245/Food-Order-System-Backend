package com.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.model.IngredientCategory;
import com.hotel.model.IngredientsItem;
import com.hotel.model.Restaurant;
import com.hotel.repository.IngredientCategoryRepository;
import com.hotel.repository.IngredientItemRepository;


@Service
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	private IngredientItemRepository ingredientItemRepository;
	
	@Autowired
	private IngredientCategoryRepository ingredientCategoryRepository;
	
	@Autowired
	private RestaurantService restaurantService;

	@Override
	public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category=new IngredientCategory();
		category.setRestaurant(restaurant);
		category.setName(name);
		return ingredientCategoryRepository.save(category);
	}

	@Override
	public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientCategory>opt=ingredientCategoryRepository.findById(id);
		if(opt.isEmpty()) {
			throw new Exception("ingredient category not found");
		}
		return opt.get();
	}

	@Override
	public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
		// TODO Auto-generated method stub
		restaurantService.findRestaurantById(id);
		return  ingredientCategoryRepository.findByRestaurantId(id);
		
	}

	@Override
	public IngredientsItem createIngredientItem(Long restaurantId, String ingredientname, Long categoryId)
			throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.findRestaurantById(restaurantId);
		IngredientCategory category=findIngredientCategoryById(categoryId);
		IngredientsItem item=new IngredientsItem();
		item.setName(ingredientname);
		item.setRestaurant(restaurant);
		item.setCategory(category);
		IngredientsItem ingredient= ingredientItemRepository.save(item);
		category.getIngredients().add(ingredient);
		return ingredient;
	}

	@Override
	public List<IngredientsItem> findRestaurantIngredients(Long restaurantId) {
		// TODO Auto-generated method stub
		return ingredientItemRepository.findByRestaurantId(restaurantId);
	}

	@Override
	public IngredientsItem updateStock(Long Id) throws Exception {
		// TODO Auto-generated method stub
		Optional<IngredientsItem> optionalIngredientsItem=ingredientItemRepository.findById(Id);
		if(optionalIngredientsItem.isEmpty()) {
			throw new Exception("ingredient not found");
		}
		
		IngredientsItem ingredientsItem=optionalIngredientsItem.get();
		ingredientsItem.setInStock(!ingredientsItem.isInStock());
		return ingredientItemRepository.save(ingredientsItem);
	}
}
