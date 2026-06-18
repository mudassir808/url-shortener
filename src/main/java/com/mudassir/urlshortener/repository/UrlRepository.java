

package com.mudassir.urlshortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mudassir.urlshortener.entity.UrlMapping;

public interface UrlRepository extends JpaRepository<UrlMapping, Long> {
	// existingLongURL()
	Optional<UrlMapping> findByLongUrl(String longUrl);
	
	// getLongURL()
	Optional<UrlMapping> findByShortKey(String ShortKey);

}