

package com.mudassir.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.mudassir.urlshortener.dto.UrlRequest;
import com.mudassir.urlshortener.dto.UrlResponce;
import com.mudassir.urlshortener.service.UrlShortenerService;

@RestController
public class UrlShortenerController {

	@Autowired
	private UrlShortenerService service;
	
	//Shorten URL API
	@PostMapping("/shorten")
	public UrlResponce shortenUrl(@RequestBody UrlRequest request) {
		
		String shortUrl = service.shortenUrl(request.getLongUrl());
		
		return new UrlResponce(shortUrl);
//		return service.shortenURL(request.getLongURL());
	}
	
	//Redirect API
	@GetMapping("/{key}")
	public UrlResponce redirectUrl(@PathVariable String key) {
		String longUrl = service.redirectUrl(key);
		return new UrlResponce(longUrl);
//		return service.redirectURL(key);
	}
}
