package com.mudassir.urlshortener.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mudassir.urlshortener.dto.LoginRequest;
import com.mudassir.urlshortener.dto.RegisterRequest;
import com.mudassir.urlshortener.entity.User;
import com.mudassir.urlshortener.repository.UserRepository;

@Service
public class AuthService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	
	public AuthService(UserRepository userRepository, 
			           BCryptPasswordEncoder passwordEncoder,
			           AuthenticationManager authenticationManager) {
		
		this.userRepository= userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager = authenticationManager;
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

	    if (authentication.isAuthenticated()) {
	        return "Login Successful";
	    }

	    return "Invalid Credentials";
	}
	
}
