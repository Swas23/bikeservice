package com.cts.dtos;

import com.cts.validation.Create;
import com.cts.validation.Update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Data Transfer Object for Customer entity.

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDto {
	private long customerId;
	@NotBlank(message = "Customer Name cannot be empty or just whitespace",groups = Create.class)
	@Pattern(regexp="[a-zA-Z ]+",message="Name must contain only alphabets",groups = {Create.class,Update.class})
	private String customerName;
	
	@NotBlank(message = "Phone number cannot be empty or just whitespace",groups = Create.class)
	@Pattern(regexp="\\d{10}",message="Mobile number should be 10 digits only",groups = {Create.class,Update.class})
	private String phoneNumber;
	
	@NotBlank(message = "Address cannot be empty or just whitespace",groups = Create.class)
	private String address;
	
	@NotBlank(message = "House Number cannot be empty or just whitespace",groups = Create.class)
	private String houseNo;
	
	@NotBlank(message = "Street cannot be empty or just whitespace",groups = Create.class)
	private String street;
	
	@NotBlank(message = "Landmark cannot be empty or just whitespace",groups = Create.class)
	private String landmark;
	
	@NotBlank(message = "City cannot be empty or just whitespace",groups = Create.class)
	private String city;
	
	@NotBlank(message = "State cannot be empty or just whitespace",groups = Create.class)
	private String state;
	
	@NotBlank(message = "Pincode cannot be empty or just whitespace",groups = Create.class)
	@Pattern(regexp = "[1-9][0-9]{5}",message = "Invalid pincode entered",groups = {Create.class,Update.class})
	private String pincode;
}
