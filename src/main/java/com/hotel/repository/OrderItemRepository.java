package com.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
