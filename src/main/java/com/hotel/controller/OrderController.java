package com.hotel.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.Response.PaymentResponse;
import com.hotel.model.CartItem;
import com.hotel.model.Order;
import com.hotel.model.User;
import com.hotel.request.AddCartItemRequest;
import com.hotel.request.OrderRequest;
import com.hotel.service.OrderService;
import com.hotel.service.PaymentService;
import com.hotel.service.UserService;

@RestController
@RequestMapping("/api")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private PaymentService paymentService;
	
	@Autowired
	private UserService userService;
	
	
	@PostMapping("/orders")
	public ResponseEntity<PaymentResponse> createOrder(@RequestBody OrderRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		
		
		User user=userService.findUserByJwtToken(jwt);
		Order order=orderService.createOrder(req, user);
		PaymentResponse res=paymentService.createPaymentLink(order);
		return new ResponseEntity<>(res,HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/orders/user")
	public ResponseEntity<List<Order>> getOrderHistory(
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		User user=userService.findUserByJwtToken(jwt);
		List<Order> orders=orderService.getUserOrder(user.getId());
		
		return new ResponseEntity<>(orders,HttpStatus.CREATED);
		
	}

}
