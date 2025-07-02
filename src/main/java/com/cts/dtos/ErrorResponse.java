package com.cts.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


// Represents a standard error response structure.
// Used to send error messages back to the client.

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ErrorResponse {
	
	public String message;

}
