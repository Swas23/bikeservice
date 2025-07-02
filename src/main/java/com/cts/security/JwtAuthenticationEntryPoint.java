package com.cts.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// Custom entry point for handling unauthorized access attempts.
// Triggered when an unauthenticated user tries to access a secured resource

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	// Sends a 401 Unauthorized response with a JSON error message when authentication fails or is missing.

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
//		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write("{\"error\": "+ authException.getMessage()+ "}");
		
	}

}
