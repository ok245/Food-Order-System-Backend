package com.hotel.service;

import java.util.List;

import com.hotel.model.Order;
import com.hotel.model.User;
import com.hotel.request.OrderRequest;

public interface OrderService {

	public Order createOrder(OrderRequest order,User user) throws Exception;
	
	public Order updateOrder(Long orderId,String orderStatus)throws Exception;
	
	public void cancelOrder(Long orderId)throws Exception;
	
	public List<Order> getUserOrder(Long userId)throws Exception;
	
	public List<Order> getRestaurantOrder(Long restaurantId,String orderStatus)throws Exception;
	
	public Order findOrderById(Long orderId)throws Exception;
}
