package com.mudassir.urlshortener.util;

import org.springframework.stereotype.Component;
//import java.util.Random;

@Component
public class KeyGenerator {
	
	private static final String BASE62 = 
			"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
//	private static final int LENGTH = 6;
//	
//	private Random random = new Random();
	
	public String generateKey(long num) {
		StringBuilder key = new StringBuilder();
		
//		for(int i=0;i< LENGTH ; i++) {
//			key.append(CHARS.charAt(random.nextInt(CHARS.length())));
//		}
		while(num>0) {
			key.append(BASE62.charAt((int)(num%62)));
			num /= 62;
		}
		
		return key.toString();
	}
}
