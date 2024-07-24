package com.hotel.service;

import java.util.List;

import com.hotel.model.Category;

public interface CategoryService {
	
	public Category createCategory(String name,Long userId) throws Exception;
	
	
	public List<Category> findCategoryByRestaurantId(Long id)throws Exception;
	
	public Category findCategoryById(Long Id)throws Exception;

}
