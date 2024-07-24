package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.model.Category;
import com.hotel.model.User;
import com.hotel.service.CategoryService;
import com.hotel.service.UserService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CategoryService categoryService;
	
	
	@PostMapping("/admin/category")
	ResponseEntity<Category> createCategory(@RequestBody Category category,
					@RequestHeader("Athorization")String jwt)throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Category createdCategory=categoryService.createCategory(category.getName(),user.getId());
		
		return new ResponseEntity<Category>(createdCategory,HttpStatus.CREATED);
		
	}

	@GetMapping("/category/restaurant")
	ResponseEntity<List<Category>> getRestaurantCategory(@RequestBody Category category,
					@RequestHeader("Athorization")String jwt)throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		List<Category> categories=categoryService.findCategoryByRestaurantId(user.getId());
		
		return new ResponseEntity<>(categories,HttpStatus.CREATED);
		
	}

	
}
