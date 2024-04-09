package com.hotel.service;

import com.hotel.model.User;

public interface UserService {

	
	public User findUserByJwtToken(String jwt) throws Exception;
	
	public User findUserByEmail(String email) throws Exception;
	
	
}
