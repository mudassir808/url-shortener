package com.mudassir.urlshortener.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mudassir.urlshortener.dto.MyUrlResponse;
import com.mudassir.urlshortener.dto.UrlRequest;
import com.mudassir.urlshortener.dto.UrlResponse;
import com.mudassir.urlshortener.service.UrlShortenerService;

@RestController
public class UrlShortenerController {

	@Autowired
	private UrlShortenerService service;
	
	//Shorten URL API
	@PostMapping("/shorten")
	public UrlResponse shortenUrl(@RequestBody UrlRequest request) {
		
		String shortUrl = service.shortenUrl(request.getLongUrl());
		
		return new UrlResponse(shortUrl);
//		return service.shortenURL(request.getLongURL());
	}
	
	//Redirect API
	@GetMapping("/{key}")
	public UrlResponse redirectUrl(@PathVariable String key) {
		String longUrl = service.redirectUrl(key);
		return new UrlResponse(longUrl);
//		return service.redirectURL(key);
	}
	
	@GetMapping("/my-urls")
	public List<MyUrlResponse> getMyUrls() {
	    return service.getMyUrls();
	}
	
	@DeleteMapping("/urls/{id}")
	public String deleteUrl(@PathVariable Long id) {

	    service.deleteUrl(id);

	    return "URL deleted successfully";
	}
}
