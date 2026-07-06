package com.mudassir.urlshortener.controller;

import org.springframework.web.bind.annotation.*;

import com.mudassir.urlshortener.dto.*;
import com.mudassir.urlshortener.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private final AuthService authService;
	
	public AuthController(AuthService authservice) {
		this.authService = authservice;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@RequestBody LoginRequest request) {

	    String token = authService.login(request);

	    return new LoginResponse(token);
	}

}
