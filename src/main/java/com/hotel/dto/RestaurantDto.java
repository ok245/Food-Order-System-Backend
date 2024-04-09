package com.hotel.dto;

import java.util.List;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
	
	private Long id;
	
	private String title;
	
	private String discription;
	
	private List<String> images;
	
	

}
