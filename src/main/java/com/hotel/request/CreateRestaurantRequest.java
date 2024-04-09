package com.hotel.request;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hotel.model.Address;
import com.hotel.model.ContactInformation;

import lombok.Data;

@Data
public class CreateRestaurantRequest {
	
    private Long id;
	
	private String name;
	
	private String discription;
	
	private String cousineType;
	
	private Address address;
	
	private ContactInformation contactInformation;
	
	private String openingHours;
	
	private List<String> images;
	
	private LocalDate registrationDate;
	
	
}
