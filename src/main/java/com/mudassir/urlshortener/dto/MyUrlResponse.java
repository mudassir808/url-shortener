package com.mudassir.urlshortener.dto;

public class MyUrlResponse {

    private String shortUrl;
    private String longUrl;
    private int clickCount;

    public MyUrlResponse(String shortUrl, String longUrl, int clickCount) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
        this.clickCount = clickCount;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public int getClickCount() {
        return clickCount;
    }
}