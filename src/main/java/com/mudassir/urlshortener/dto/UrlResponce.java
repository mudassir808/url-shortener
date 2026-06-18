package com.mudassir.urlshortener.dto;

public class UrlResponce {

	private String shortUrl;
	
	public UrlResponce(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

}
