package com.hotel.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hotel.dto.RestaurantDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO )
	private Long id;
	
	@ManyToOne
	private Food food;
	
	private Long totalPrice;
	
	private int quantity;
	
	private List<String> ingredients;

}
