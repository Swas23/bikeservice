package com.cts.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Utility class to generate encrypted passwords using BCrypt.
public class PasswordGenerator {
	
	public static void main(String[] args) {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("swas@123"));
		System.out.println(encoder.encode("ram@123"));
	}

}
