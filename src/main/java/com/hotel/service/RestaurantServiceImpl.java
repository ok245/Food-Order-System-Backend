package com.hotel.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.dto.RestaurantDto;
import com.hotel.model.Address;
import com.hotel.model.Restaurant;
import com.hotel.model.User;
import com.hotel.repository.AddressRepository;
import com.hotel.repository.RestaurantRepository;
import com.hotel.repository.UserRepository;
import com.hotel.request.CreateRestaurantRequest;
@Service
public class RestaurantServiceImpl implements RestaurantService {
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository; 

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
		// TODO Auto-generated method stub
		Address address=addressRepository.save(req.getAddress());
		Restaurant restaurant=new Restaurant(); 
		restaurant.setAddress(address);
		restaurant.setContactInformation(req.getContactInformation());
		restaurant.setCousineType(req.getCousineType());
		restaurant.setDiscription(req.getDiscription());
		restaurant.setImages(req.getImages());
		restaurant.setName(req.getName());
		restaurant.setOpeningHours(req.getOpeningHours());
		restaurant.setRegistrationDate(LocalDate.now());
		restaurant.setOwner(user);
		return restaurantRepository.save(restaurant);
		
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=findRestaurantById(restaurantId);
		
		if(restaurant.getCousineType()!=null) {
			restaurant.setCousineType(updatedRestaurant.getCousineType());
		}
		
		if(restaurant.getName()!=null) {
			restaurant.setName(updatedRestaurant.getName());
		}
		
		if(restaurant.getDiscription()!=null) {
			restaurant.setDiscription(updatedRestaurant.getDiscription());
		}
		return restaurant;
	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=findRestaurantById(restaurantId);
		restaurantRepository.delete(restaurant);

	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		// TODO Auto-generated method stub
		return restaurantRepository.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		// TODO Auto-generated method stub
		return restaurantRepository.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findRestaurantById(Long restaurantId) throws Exception {
		// TODO Auto-generated method stub
		Optional<Restaurant> opt=restaurantRepository.findById(restaurantId);
		if(opt.isEmpty()) {
			throw new Exception("restaurant is not found with id"+restaurantId);
		}
		return opt.get();
	}

	@Override
	public Restaurant getRestaurantByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
		if(restaurant==null) {
			throw new Exception("restaurant not found with owner id"+userId);
		}
			return restaurant;
	}

	@Override
	public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
		// TODO Auto-generated method stub
		Restaurant restaurant=findRestaurantById(restaurantId);
		RestaurantDto dto=new RestaurantDto();
		dto.setDiscription(restaurant.getDiscription());
		dto.setImages(restaurant.getImages());
		dto.setTitle(restaurant.getName());
		dto.setId(restaurantId);
		
		boolean isFavorite=false;
		List <RestaurantDto> favorites= user.getFavorites();
		
		for(RestaurantDto favorite : favorites) {
			
			if(favorite.getId().equals(restaurantId)) {
				
				isFavorite=true;
				break;
				
			}
			
		}
		
		if(isFavorite) {
			favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
		}else {
			favorites.add(dto);
		}
		
		userRepository.save(user);
		return  dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(Long id) throws Exception {
		// TODO Auto-generated method stub
		 Restaurant restaurant=findRestaurantById(id);
		 restaurant.setOpen(!restaurant.isOpen());
		 return restaurantRepository.save(restaurant); 
	}

}
