package com.mudassir.urlshortener.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mudassir.urlshortener.dto.*;
import com.mudassir.urlshortener.entity.User;
import com.mudassir.urlshortener.repository.UserRepository;
import com.mudassir.urlshortener.security.JwtService;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthService(UserRepository userRepository, 
			           BCryptPasswordEncoder passwordEncoder,
			           AuthenticationManager authenticationManager,
			           JwtService jwtService) {
		
		this.userRepository= userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	
	
	
	public String register (RegisterRequest request) {
		
		// Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email already registered!";
        }
        
     // Create User entity
        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        
        String encryptedPassword = passwordEncoder.encode(request.getPassword());
        user.setPassword(encryptedPassword);

        // Default role
        user.setRole("USER");

        // Save into database
        userRepository.save(user);

        return "User Registered Successfully";
	}
	
	
	
	public String login(LoginRequest request) {

	    Authentication authentication =
	            authenticationManager.authenticate(

	                    new UsernamePasswordAuthenticationToken(
	                            request.getEmail(),
	                            request.getPassword()
	                    )
	            );
	    
//	    The object you receive as AuthenticationManager is actually a ProviderManager (an implementation of the AuthenticationManager interface).
//	    ProviderManager is configured by Spring with all AuthenticationProvider beans found in the application context.
//	    When you call authenticate(), ProviderManager delegates the work to the appropriate provider—in your case, DaoAuthenticationProvider.
	    
	    

	    if (authentication.isAuthenticated()) {
	    	//return jwtService.generateToken(request.getEmail());
	    	
	    	String token = jwtService.generateToken(request.getEmail());

	    	System.out.println(jwtService.extractUsername(token));

	    	System.out.println(jwtService.isTokenValid(
	    	        token,
	    	        request.getEmail()));

	    	return token;
	    }

	    return "Invalid Credentials";
	}
	
}
