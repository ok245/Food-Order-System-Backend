package com.hotel.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.model.Address;
import com.hotel.model.Cart;
import com.hotel.model.CartItem;
import com.hotel.model.Order;
import com.hotel.model.OrderItem;
import com.hotel.model.Restaurant;
import com.hotel.model.User;
import com.hotel.repository.AddressRepository;
import com.hotel.repository.OrderItemRepository;
import com.hotel.repository.OrderRepository;
import com.hotel.repository.UserRepository;
import com.hotel.request.OrderRequest;

@Service
public class OrderServiceImpl implements OrderService {

	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RestaurantService restaurantService; 
	
	@Autowired
	private CartService cartService;
	
	@Override
	public Order createOrder(OrderRequest order, User user)throws Exception {
		// TODO Auto-generated method stub
		Address shippAddress=order.getDeliveryAddress();
		Address savedAddress=addressRepository.save(shippAddress);
		
		if(!user.getAddresses().contains(savedAddress)) {
			user.getAddresses().add(savedAddress);
			userRepository.save(user);
		}
		
		Restaurant restaurant=restaurantService.findRestaurantById(order.getRestaurantId());
		
		Order createdOrder=new Order();
		createdOrder.setCustomer(user);
		createdOrder.setCreatedAt(new Date());
		createdOrder.setOrderStatus("PENDING");
		createdOrder.setDeliveryAddress(savedAddress);
		createdOrder.setRestaurant(restaurant);
		
		Cart cart=cartService.findCartByUserId(user.getId());
		List<OrderItem> orderItems=new ArrayList<OrderItem>();
		
		for(CartItem cartItem:cart.getItem()) {
			OrderItem orderItem=new OrderItem();
			orderItem.setFood(cartItem.getFood());
			orderItem.setIngredients(cartItem.getIngrdient());
		    orderItem.setTotalPrice(cartItem.getTotalPrice());
		    orderItem.setQuantity(cartItem.getQuantity());
		    OrderItem savedOrderItem=orderItemRepository.save(orderItem);
		    orderItems.add(savedOrderItem);
		}
		Long totalPrice=cartService.calculateCartTotals(cart);
		createdOrder.setItems(orderItems);
		createdOrder.setTotalPrice(totalPrice);
		
		Order savedOrder=orderRepository.save(createdOrder);
		restaurant.getOrder().add(savedOrder);
		return createdOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		Order order=findOrderById(orderId);
		if(orderStatus.equals("OUT_FOR_DELIVERY") ||orderStatus.equals("DELIVERED")||
				orderStatus.equals("COMPLETED")||orderStatus.equals("PENDING")) {
			order.setOrderStatus(orderStatus);
			return orderRepository.save(order);
		}
		
		throw new Exception("Please select valid order status");
		
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		Order order=findOrderById(orderId);
		orderRepository.delete(order);

	}

	@Override
	public List<Order> getUserOrder(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return orderRepository.findBycustomerId(userId);
	}

	@Override
	public List<Order> getRestaurantOrder(Long restaurantId, String orderStatus) throws Exception {
		// TODO Auto-generated method stub
		List<Order>orders=orderRepository.findByRestaurantId(restaurantId);
		
		if(orderStatus!=null) {
			orders=orders.stream().filter(order->order.getOrderStatus().equals(orderStatus)).collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		// TODO Auto-generated method stub
		Optional<Order>optionalOrder =orderRepository.findById(orderId);
		if(optionalOrder.isEmpty()) {
			throw new Exception("Order not found");
		}
		return optionalOrder.get();
	}

}
