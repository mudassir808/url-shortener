package com.mudassir.urlshortener.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.mudassir.urlshortener.dto.MyUrlResponse;
import com.mudassir.urlshortener.entity.UrlMapping;
import com.mudassir.urlshortener.entity.User;
import com.mudassir.urlshortener.repository.UrlRepository;
import com.mudassir.urlshortener.repository.UserRepository;
import com.mudassir.urlshortener.util.KeyGenerator;

@Service
public class UrlShortenerService {

	@Autowired 
	private UrlRepository repository;
	
	@Autowired
	private KeyGenerator keyGenerator;
	
	private final UserRepository userRepository;
	
	public UrlShortenerService(UserRepository userRepository) {
//            KeyGenerator keyGenerator,	
//            UrlRepository repository) {

              //this.repository = repository;
              //this.keyGenerator = keyGenerator;
              this.userRepository = userRepository;
            }
	
	public String shortenUrl(String longUrl) {
		
		Authentication authentication =
		        SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		User user = userRepository.findByEmail(email)
		        .orElseThrow(() -> new RuntimeException("User not found"));
		
		
		
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
		mapping.setUser(user);
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
	
	public List<MyUrlResponse> getMyUrls() {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    List<UrlMapping> urls = repository.findByUser(user);

	    return urls.stream()
	            .map(url -> new MyUrlResponse(
	                    "http://short.ly/" + url.getShortKey(),
	                    url.getLongUrl(),
	                    url.getClickCount()))
	            .toList();
	}
	
	
	public void deleteUrl(Long id) {

	    Authentication authentication =
	            SecurityContextHolder.getContext().getAuthentication();

	    String email = authentication.getName();

	    User currentUser = userRepository.findByEmail(email)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    UrlMapping url = repository.findById(id)
	            .orElseThrow(() -> new RuntimeException("URL not found"));

	    if (!url.getUser().getId().equals(currentUser.getId())) {
	        throw new RuntimeException("You are not authorized to delete this URL");
	    }

	    repository.delete(url);
	}
}
