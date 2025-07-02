package com.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cts.dtos.LoginDto;
import com.cts.security.JwtTokenProvider;

// Implementation of the AuthService interface.
// Handles user authentication and JWT token generation.

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager; 
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	
	// Authenticates the user using username/email and password,
	// and returns a JWT token if authentication is successful.

	@Override
	public String login(LoginDto loginDto) {

		
		UsernamePasswordAuthenticationToken authToken = 
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), loginDto.getPassword());
		
		Authentication authentication =  authenticationManager.authenticate(authToken);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String token = jwtTokenProvider.generateToken(authentication);
		
		return token;
	}

}
