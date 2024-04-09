package com.hotel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotel.model.User;

public interface UserRepository extends JpaRepository<User,Long> {

	
	public User findByEmail(String username);
	
	
}
