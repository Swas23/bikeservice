package com.cts.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Data Transfer Object for login requests.
// Carries user credentials for authentication.

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDto {
	
	private String usernameOrEmail;
	private String password;

}
