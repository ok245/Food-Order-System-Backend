package com.hotel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.model.Category;
import com.hotel.model.Restaurant;
import com.hotel.repository.CategoryRepository;


@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	
	@Autowired
	private CategoryRepository categoryRepository;
	

	@Override
	public Category createCategory(String name, Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
		Category category=new Category();
		category.setName(name);
		category.setRestaurant(restaurant);
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantService.getRestaurantByUserId(id);
		return categoryRepository.findByRestaurantId(restaurant.getId());
	}

	@Override
	public Category findCategoryById(Long Id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Category> optionalCategory=categoryRepository.findById(Id) ;
		if(optionalCategory.isEmpty()) {
			throw new Exception("Category is not found");
	
	}
		return optionalCategory.get();
	}
}