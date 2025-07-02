package com.cts.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.cts.exceptions.BikeServiceExceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;


// Utility class for generating, parsing, and validating JWT tokens.

@Component
public class JwtTokenProvider {
	
	@Value("${app.jwt.secret}")
	private String secretKey;
	
	@Value("${app.jwt.expiry-millis}")
	private long expiryMillis;
	
	// Generates a JWT token for the authenticated user.
	// @return a signed JWT token

	public String generateToken(Authentication authentication) {
		
		String username = authentication.getName();
		
		Date currentDate = new Date();
		
		Date expirydate = new Date(currentDate.getTime()+expiryMillis);
		
		
		String token = 
				Jwts.builder()
				.subject(username)
				.issuedAt(currentDate)
				.expiration(expirydate)
				.signWith(key())
				.compact();
				
		return token;
		
	}
	
	// Returns the signing key used for JWT creation and validation.
	// @return a cryptographic key derived from the secret
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
	}
	
	
	// Extracts the username from a valid JWT token.
	// @return the username embedded in the token

	public String getUsername(String token) {
		
		String username =  Jwts.parser().verifyWith((SecretKey) key())
		.build().parseSignedClaims(token)
		.getPayload().getSubject();
		
		return username;
	}
	
	
	// Validates the JWT token and throws custom exceptions for different failure cases.
	// @return true if the token is valid
    // @throws BikeServiceExceptions if the token is invalid or expired

	public boolean validateToken(String token) {
		try {
			
			Jwts.parser().verifyWith((SecretKey)key())
			.build().parse(token);
			
			return true;
			
		}
		catch(MalformedJwtException | SignatureException ex) {
			throw new BikeServiceExceptions(HttpStatus.BAD_REQUEST,"Invalid token");
		}
		catch(ExpiredJwtException ex) {
			throw new BikeServiceExceptions(HttpStatus.BAD_REQUEST,"Expired Token");
		}
		catch(UnsupportedJwtException ex) {
			throw new BikeServiceExceptions(HttpStatus.BAD_REQUEST,"Unsupported token");
		}
		catch(IllegalArgumentException ex) {
			throw new BikeServiceExceptions(HttpStatus.BAD_REQUEST,"Token Claims String is null or empty");
		}
	}
}
