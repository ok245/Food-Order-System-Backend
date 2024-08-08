package com.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.model.IngredientCategory;
import com.hotel.model.IngredientsItem;
import com.hotel.request.IngredientCategoryRequest;
import com.hotel.request.IngredientRequest;
import com.hotel.service.IngredientService;

@RestController
@RequestMapping("/api/admin/ingredients")
public class IngredientController {

	@Autowired
	private IngredientService ingredientService;
	
	
	@PostMapping("/category")
	public ResponseEntity<IngredientCategory> createIngredientcategory(
			@RequestBody IngredientCategoryRequest req
			) throws Exception {
		
		IngredientCategory item=ingredientService.createIngredientCategory(req.getName(),req.getRestaurantId());
		return new ResponseEntity<>(item,HttpStatus.CREATED);
		
	}
	
	@PostMapping()
	public ResponseEntity<IngredientsItem> createIngredientItem(
			@RequestBody IngredientRequest req
			) throws Exception {
		
		System.out.println("createIngredientItem LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL "+req);
		
		IngredientsItem item=ingredientService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
		return new ResponseEntity<>(item,HttpStatus.CREATED);
		
	}
	
	@PutMapping("/{id}/stock")
	public ResponseEntity<IngredientsItem> updateIngredientstock(
			@PathVariable Long id,
			@RequestBody IngredientRequest req
			) throws Exception {
		
		IngredientsItem item=ingredientService.createIngredientItem(req.getRestaurantId(),req.getName(),req.getCategoryId());
		return new ResponseEntity<>(item,HttpStatus.CREATED);
		
	}
	
	@GetMapping("restaurant/{id}")
	public ResponseEntity<List<IngredientsItem>> getRestaurantIngredient(
			@PathVariable Long id
			) throws Exception {
		
		List<IngredientsItem> item=ingredientService.findRestaurantIngredients(id);
		return new ResponseEntity<>(item,HttpStatus.CREATED);
		
	}
	
	@GetMapping("restaurant/{id}/category")
	public ResponseEntity<List<IngredientCategory>> getRestaurantIngredientCategory(
			@PathVariable Long id
			) throws Exception {
		
		List<IngredientCategory> item=ingredientService.findIngredientCategoryByRestaurantId(id);
		return new ResponseEntity<>(item,HttpStatus.CREATED);
		
	}
}
