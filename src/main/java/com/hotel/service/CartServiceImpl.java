package com.hotel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.model.Cart;
import com.hotel.model.CartItem;
import com.hotel.model.Food;
import com.hotel.model.User;
import com.hotel.repository.CartItemRepository;
import com.hotel.repository.CartRepository;
import com.hotel.repository.FoodRepository;
import com.hotel.request.AddCartItemRequest;
@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FoodService foodService;
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public CartItem addItemTocart(AddCartItemRequest req, String jwt) throws Exception {
		// TODO Auto-generated method stub
		User user=userService.findUserByJwtToken(jwt);
		Food food=foodService.findFoodById(req.getFoodId());
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		for(CartItem cartItem:cart.getItem()) {
			if(cartItem.getFood().equals(food)) {
				int newQuantity=cartItem.getQuantity()+req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(), newQuantity);
			}
		}
		
		CartItem newCartItem=new CartItem();
		newCartItem.setFood(food);
		newCartItem.setCart(cart);
		newCartItem.setQuantity(req.getQuantity());
		newCartItem.setIngrdient(req.getIngredients());
		newCartItem.setTotalPrice(req.getQuantity()*food.getPrice());
		
		CartItem savedCartItem=cartItemRepository.save(newCartItem);
		cart.getItem().add(savedCartItem);
		//cartRepository.save(cart);
		
		return savedCartItem;
	}

	@Override
	public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		// TODO Auto-generated method stub
		Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("cart item not found");
		}
		
		CartItem item=cartItemOptional.get();
		item.setQuantity(quantity);
		item.setTotalPrice(item.getFood().getPrice()*quantity);
		cartItemRepository.save(item);
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		// TODO Auto-generated method stub
		User user=userService.findUserByJwtToken(jwt);
		
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		Optional<CartItem> cartItemOptional=cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("cart item not found");
		}
		
		CartItem item=cartItemOptional.get();
		cart.getItem().remove(item);
		cartRepository.save(cart);
		return cartRepository.save(cart);
	}

	@Override
	public Long calculateCartTotals(Cart cart) throws Exception {
		// TODO Auto-generated method stub
		Long total=0L;
		
		for(CartItem cartItem:cart.getItem()) {
			total+=cartItem.getFood().getPrice()*cartItem.getQuantity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		// TODO Auto-generated method stub
		Optional<Cart> cartOptional=cartRepository.findById(id);
		if(cartOptional.isEmpty()) {
			throw new Exception("cart is not found");
		}
		
		return cartOptional.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		//User user=userService.findUserByJwtToken(jwt);
		Cart cart= cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotals(cart));
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		//User user=userService.findUserByJwtToken(jwt);
		// TODO Auto-generated method stub
		Cart cart=findCartByUserId(userId);
		cart.getItem().clear();
		return cartRepository.save(cart);
	}

}
