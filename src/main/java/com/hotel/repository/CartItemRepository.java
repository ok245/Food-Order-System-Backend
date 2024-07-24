package com.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

}
