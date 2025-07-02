package com.cts.exceptions;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Custom exception class for handling bike service-related errors.

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeServiceExceptions extends RuntimeException{
	
	private HttpStatus status;
	
	private String message;

}
