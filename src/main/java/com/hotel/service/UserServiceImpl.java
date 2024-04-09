package com.hotel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotel.config.JwtProvider;
import com.hotel.model.User;
import com.hotel.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtProvider jwtProvider;
	@Override
	public User findUserByJwtToken(String jwt) throws Exception {
		// TODO Auto-generated method stub
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		User user=findUserByEmail(email);
		return user;
	}

	@Override
	public User findUserByEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		User user=userRepository.findByEmail(email);
		if(user==null) {
			throw new Exception("User not Found");
		}
		return user;
	}

}
