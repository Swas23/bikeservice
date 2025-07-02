package com.cts.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cts.dtos.ErrorResponse;
import com.cts.exceptions.BikesNotFoundException;


// Global exception handler for the application.
// Catches and handles various exceptions thrown by controllers.

@ControllerAdvice
public class ErrorHandler {
	
	
	// Handles BikesNotFoundException when a requested bike is not found.
	// @return ResponseEntity with error message and 404 status

	
	@ExceptionHandler(BikesNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleCustomerNotFound(BikesNotFoundException ex){
		
		ErrorResponse response=new ErrorResponse();
		response.setMessage(ex.getMessage());
		
		return new ResponseEntity<ErrorResponse>(response,HttpStatus.NOT_FOUND);
	}
	
	
	// Handles validation errors for request bodies with invalid fields.
	// @return ResponseEntity with combined error messages and 400 status
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex){
		
		StringBuilder sb=new StringBuilder();
		for(FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			
			sb.append(fieldError.getField()+" : "+fieldError.getDefaultMessage()+" ");
			
		}
		ErrorResponse err = new ErrorResponse(sb.toString());
		
		return ResponseEntity.badRequest().body(err);
		
	}
	
	
	// Handles cases where an unsupported HTTP method is used on an endpoint.
	// @return ResponseEntity with error message and 405 status
	
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleInvalidHttpRequest(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse response = new ErrorResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }
 
    
    // Handles all other unexpected exceptions in the application
    // @return ResponseEntity with generic error message and 500 status

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
//        ErrorResponse response = new ErrorResponse("Unexpected error: " + ex.getMessage());
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//    }

}
