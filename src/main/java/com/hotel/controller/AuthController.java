package com.hotel.controller;

import java.util.Collection;

import org.hibernate.bytecode.spi.ReflectionOptimizer.AccessOptimizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotel.Response.AuthResponse;
import com.hotel.config.JwtProvider;
import com.hotel.model.Cart;
import com.hotel.model.USER_ROLE;
import com.hotel.model.User;
import com.hotel.repository.CartRepository;
import com.hotel.repository.UserRepository;
import com.hotel.request.LoginRequest;
import com.hotel.service.CustomUserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	public AuthController() {
		System.out.print("in auth controler @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
	}
	
	@GetMapping("/home")
	public void Home() {
		System.out.print("in home page XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}


	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtProvider JwtProvider;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsServices;
	
	@Autowired
	private CartRepository cartRepository;
	
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse>createUserhandler(@RequestBody User user) throws Exception{
		
		User isEmailExist=userRepository.findByEmail(user.getEmail());
		if(isEmailExist!=null) {
			throw new Exception("Email already used with another acount");
		}
		
		User createdUser=new User();
		createdUser.setEmail(user.getEmail());
		createdUser.setFullName(user.getFullName());
		createdUser.setRole(user.getRole());
		createdUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser=userRepository.save(createdUser);
		
		Cart cart=new Cart();
		
		cart.setCustomer(savedUser);
		cartRepository.save(cart);
		
		Authentication authentication=new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt=JwtProvider.generateToken(authentication);
		
		AuthResponse authResponse =new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Register Success");
		authResponse.setRole(savedUser.getRole());
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	}
	
	@PostMapping("/signin")
	public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest req){
		System.out.println("in Sign in pageppppppppppppppppppppppppppppppppppppppppppppppppppppppppp "+req.toString());
		
		String username=req.getEmail();
		String password=req.getPassword();
		
		Authentication authentication=authenticate(username,password);
		Collection<? extends GrantedAuthority> authorities=authentication.getAuthorities();
		String role=authorities.isEmpty()?null: authorities.iterator().next().getAuthority();
		
        
		String jwt=JwtProvider.generateToken(authentication);
		System.out.println("in Sign in JWT%%%%%%%%%%%%%%%%%%%%%%%%%%% "+jwt);
		AuthResponse authResponse =new AuthResponse();
		authResponse.setJwt(jwt);
		authResponse.setMessage("Login Success");
		authResponse.setRole(USER_ROLE.valueOf(role));
		
		return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
		
		
	}


	private Authentication authenticate(String username, String password) {
		// TODO Auto-generated method stub
		UserDetails userDetails=customUserDetailsServices.loadUserByUsername(username);
		
		if(userDetails==null) {
			throw new BadCredentialsException("invalid username");
			
			}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("invalid password....");
		}
		return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
	}
	
}
