package com.hotel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.model.Cart;
import com.hotel.model.CartItem;
import com.hotel.model.User;
import com.hotel.request.AddCartItemRequest;
import com.hotel.request.UpdateCartItemRequest;
import com.hotel.service.CartService;
import com.hotel.service.UserService;

@RestController
@RequestMapping("/api")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private UserService userService;
	
	@PutMapping("/cart/add")
	public ResponseEntity<CartItem> addToCartItem(@RequestBody AddCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		CartItem cartItem=cartService.addItemTocart(req, jwt);
		return new ResponseEntity<>(cartItem,HttpStatus.OK);
		
	}
	
	@PutMapping("/cart-item/update")
	public ResponseEntity<CartItem> updatecartItemQuantity(@RequestBody UpdateCartItemRequest req,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		CartItem cartItem=cartService.updateCartItemQuantity(req.getCartItemId(),req.getQuantity());
		return new ResponseEntity<>(cartItem,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/cart-item/{id}/remove")
	public ResponseEntity<Cart> removeCartItem(@RequestBody UpdateCartItemRequest req,
			@PathVariable Long id,
			@RequestHeader("Authorization") String jwt) throws Exception{
		
		Cart cart=cartService.removeItemFromCart(id, jwt);
		return new ResponseEntity<>(cart,HttpStatus.OK);
		
	}
	
	@PutMapping("/cart/clear")
	public ResponseEntity<Cart> clearCart(
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartService.clearCart(user.getId());
		return new ResponseEntity<>(cart,HttpStatus.OK);
		
	}
	
	@GetMapping("/cart")
	public ResponseEntity<Cart> findUserCart(
			@RequestHeader("Authorization") String jwt) throws Exception{
		User user=userService.findUserByJwtToken(jwt);
		Cart cart=cartService.findCartByUserId(user.getId());
		return new ResponseEntity<>(cart,HttpStatus.OK);
		
	}
	
}
