package com.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
	public List<Order> findBycustomerId(Long userId);
	
	public List<Order> findByRestaurantId(Long userId);

}
