package com.cts.exceptions;


// Custom exception thrown when a bike is not found in the system

public class BikesNotFoundException extends RuntimeException{

	public BikesNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BikesNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	

}
