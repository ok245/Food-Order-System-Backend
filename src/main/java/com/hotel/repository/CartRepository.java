package com.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.Cart;

public interface CartRepository extends JpaRepository<Cart,Long> {

}
