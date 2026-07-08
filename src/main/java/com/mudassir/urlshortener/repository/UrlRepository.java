package com.mudassir.urlshortener.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mudassir.urlshortener.entity.UrlMapping;
import com.mudassir.urlshortener.entity.User;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
	// existingLongURL()
	Optional<UrlMapping> findByLongUrl(String longUrl);
	
	// getLongURL()
	Optional<UrlMapping> findByShortKey(String ShortKey);
	
	List<UrlMapping> findByUser(User user);

}