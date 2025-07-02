package com.cts.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// Represents the response returned after successful authentication.
//Contains the JWT token and its type.

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {
	
	private String jwtToken;
	
	private String type="Bearer";

}
