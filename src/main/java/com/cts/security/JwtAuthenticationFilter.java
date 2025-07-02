package com.cts.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.cts.exceptions.BikeServiceExceptions;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


// Custom filter that intercepts each request to validate the JWT token.
// If the token is valid, it sets the authentication in the security context

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	// Filters incoming requests to extract and validate JWT tokens.
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
		String token = getTokenFromRequest(request);

		if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {

			String username = jwtTokenProvider.getUsername(token);

			UserDetails userDetails = userDetailsService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
					userDetails.getAuthorities());

			authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

			SecurityContextHolder.getContext().setAuthentication(authToken);
		}
		} catch(BikeServiceExceptions e) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": " + e.getMessage() + "}");
			return;
		}

		filterChain.doFilter(request, response);
	}

	
	// Extracts the JWT token from the Authorization header
	// @return the JWT token if present, otherwise null

	private String getTokenFromRequest(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {

			String token = bearerToken.substring(7);
			return token;
		}
		return null;
	}

}
