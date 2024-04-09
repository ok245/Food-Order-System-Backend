package com.hotel.request;

import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Category;
import com.hotel.model.IngredientsItem;
import com.hotel.model.Restaurant;


import lombok.Data;

@Data
public class CreateFoodRequest {
	
	private String name;
	
	private String discription;
	
	private Long price;
	

	private Category category;
	
	
	private List<String> images;
	
	private boolean available;
	

	private Long restaurantId;
	
	private boolean vegeterian;
	
	private boolean isSeasonal;
	
	
	List<IngredientsItem> ingredients;
	

}
