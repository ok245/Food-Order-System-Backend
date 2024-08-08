package com.hotel.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.model.Food;
import com.hotel.model.Restaurant;
import com.hotel.model.User;
import com.hotel.request.CreateFoodRequest;
import com.hotel.service.FoodService;
import com.hotel.service.RestaurantService;
import com.hotel.service.UserService;

@RestController
@RequestMapping("/api/food")
public class FoodController {
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@GetMapping("/search")
	public ResponseEntity<List<Food>> searchFood(@RequestParam String name,
			@RequestHeader("Authorization") String jwt)throws Exception
	{
		User user=userService.findUserByJwtToken(jwt);
		List<Food>foods=foodService.searchFood(name);
		return new ResponseEntity<>(foods,HttpStatus.CREATED);
	}
	
	
	@GetMapping("/restaurant/{restaurantId}")
	public ResponseEntity<List<Food>> getRestaurantFood(@RequestParam (required = false) boolean vegetarian,
			@RequestParam  (required = false) boolean seasonal,
			@RequestParam (required = false) boolean nonveg,
			@RequestParam (required = false) String food_category,
			@PathVariable Long restaurantId,
			@RequestHeader("Authorization") String jwt)throws Exception
	{
		System.out.print(vegetarian+""+""+seasonal+""+""+food_category);
		User user=userService.findUserByJwtToken(jwt);
		List<Food>foods=foodService.getRestaurantsFoods(restaurantId, vegetarian, nonveg, seasonal, food_category);
		return new ResponseEntity<>(foods,HttpStatus.OK);
	}

}
