package com.hotel.service;

import java.util.List;

import com.hotel.dto.RestaurantDto;
import com.hotel.model.Restaurant;
import com.hotel.model.User;
import com.hotel.request.CreateRestaurantRequest;

public interface RestaurantService {
	
	public Restaurant createRestaurant(CreateRestaurantRequest req,User user) ;
	
	public Restaurant updateRestaurant(Long id,CreateRestaurantRequest req) throws Exception;
	
	public void deleteRestaurant(Long restaurantId)throws Exception;
	
	public List<Restaurant> getAllRestaurant();
	
	public List<Restaurant> searchRestaurant(String keyword);
	
	public Restaurant findRestaurantById(Long restaurantId)throws Exception;
	
	public Restaurant getRestaurantByUserId(Long userId) throws Exception ;
	
	public RestaurantDto addToFavorites(Long restaurantId,User user)throws Exception;
	
	public Restaurant updateRestaurantStatus(Long id)throws Exception;
		
	

}
