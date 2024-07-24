package com.hotel.request;

import com.hotel.model.Address;

import lombok.Data;

@Data
public class OrderRequest {

	private Long restaurantId;
	
	private Address deliveryAddress;
}
