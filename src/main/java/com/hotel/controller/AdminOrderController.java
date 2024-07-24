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

import com.hotel.model.Order;
import com.hotel.model.User;
import com.hotel.request.OrderRequest;
import com.hotel.service.OrderService;
import com.hotel.service.UserService;

@RestController
@RequestMapping("api/admin")
public class AdminOrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping("/order/restaurant/{id}")
	public ResponseEntity<List<Order>> getOrderHistory(
			@PathVariable Long id,
			@RequestParam(required = true)String order_status,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Order> orders=orderService.getRestaurantOrder(id,order_status);
		
		return new ResponseEntity<>(orders,HttpStatus.OK);
		
	}
	
	@GetMapping("/order/{id}/{orderStatus}")
	public ResponseEntity<Order> updateOrderStatus(
			@PathVariable Long id,
			@PathVariable String orderStatus,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		Order orders=orderService.updateOrder(id, orderStatus);
		
		return new ResponseEntity<>(orders,HttpStatus.OK);
		
	}

}
