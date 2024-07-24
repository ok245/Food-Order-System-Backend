package com.hotel.service;

import com.hotel.model.Cart;
import com.hotel.model.CartItem;
import com.hotel.request.AddCartItemRequest;

public interface CartService {

	
	public CartItem addItemTocart(AddCartItemRequest req,String jwt)throws Exception;
	
	public CartItem updateCartItemQuantity(Long cartItemId,int quantity)throws Exception;
	
	public Cart removeItemFromCart(Long cartItemId,String jwt)throws Exception;
	
	public Long calculateCartTotals(Cart cart)throws Exception;
	
	public Cart findCartById(Long id)throws Exception;
	
	public Cart findCartByUserId(Long userId)throws Exception;
	
	public Cart clearCart(Long userId)throws Exception;
}
