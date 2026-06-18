package com.mudassir.urlshortener.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mudassir.urlshortener.entity.UrlMapping;
import com.mudassir.urlshortener.repository.UrlRepository;
import com.mudassir.urlshortener.util.KeyGenerator;

@Service
public class UrlShortenerService {

	@Autowired  //
	private UrlRepository repository;
	
	@Autowired
	private KeyGenerator keyGenerator;
	
	public String shortenUrl(String longUrl) {
		
		if(longUrl == null || longUrl.isEmpty()) {
			return "Invalid URL!";
		}
		
		//Check if already exist
		Optional<UrlMapping> existing = repository.findByLongUrl(longUrl);
		
		if(existing.isPresent()) {
			return "http://short.ly/" + existing.get().getShortKey();
		}
		
		//generate new key
		
		
		
		//save to DB
		UrlMapping mapping = new UrlMapping();
		mapping.setLongUrl(longUrl);
		UrlMapping saved = repository.save(mapping);
		
		//generate new shortKey
		String shortKey = keyGenerator.generateKey(saved.getId());
		saved.setShortKey(shortKey);
		
		repository.save(saved);
		
		return "http://short.ly/" + shortKey;
	}
	
	//REDIRECT
	public String redirectUrl(String shortKey) {
		
		if (shortKey == null || shortKey.isEmpty()) {
			return "Invalid short key!";
		}
		
		Optional<UrlMapping> result = repository.findByShortKey(shortKey);
		
		if(result.isEmpty()) {
			return "URL not found!";
		}
		
		UrlMapping mapping = result.get();
		
		//increment count
		mapping.setClickCount(mapping.getClickCount() + 1);
		
		//save updated value
		repository.save(mapping);
		
		return mapping.getLongUrl();
		//return result.get().getLongURL();
	}
}
