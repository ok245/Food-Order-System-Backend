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

import com.hotel.dto.RestaurantDto;
import com.hotel.model.Restaurant;
import com.hotel.model.User;
import com.hotel.request.CreateRestaurantRequest;
import com.hotel.service.RestaurantService;
import com.hotel.service.UserService;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/search")
	public ResponseEntity<List<Restaurant>> searchRestaurant(
			@RequestHeader("Authorization") String jwt,
			@RequestParam String keyword) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant=restaurantService.searchRestaurant(keyword);
		
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
		
	}
	
	
	
	@GetMapping()
	public ResponseEntity<List<Restaurant>> getAllRestaurant(
			@RequestHeader("Authorization") String jwt
			) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Restaurant> restaurant=restaurantService.getAllRestaurant();
		
		return new ResponseEntity<>(restaurant,HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/{id}")
	public ResponseEntity<Restaurant> findRestaurantById(
			@RequestHeader("Authorization") String jwt,
			
			@PathVariable Long id
			) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Restaurant restaurant=restaurantService.findRestaurantById(id);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
	
	
	@PostMapping("/{id}/add-favorites")
	public ResponseEntity<RestaurantDto> addToFavorites(
			@RequestHeader("Authorization") String jwt,
			@PathVariable Long id
			) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		RestaurantDto restaurant=restaurantService.addToFavorites(id, user);
		
		return new ResponseEntity<>(restaurant,HttpStatus.OK);
		
	}
}
