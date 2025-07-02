package com.cts.dtos;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.cts.entities.Customer;
import com.cts.validation.Create;
import com.cts.validation.Update;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Data Transfer Object for Bike entity.

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeDto {

	private long bikeId;
	
	@NotBlank(message = "Bike make cannot be empty or just whitespace",groups = Create.class)
	private String bikeMake;
	
	@NotBlank(message = "Model name cannot be empty or just whitespace",groups = Create.class)
	private String modelName;
	
	@NotBlank(message = "Bike Registration number cannot be empty or just whitespace",groups = Create.class)
	@Pattern(regexp ="[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}", message = "Enter valid Bike Registration Number",groups = {Create.class,Update.class})
	private String bikeRegistrationNumber;
	
	@NotBlank(message = "Bike Chasis number cannot be empty or just whitespace",groups = Create.class)
	@Pattern(regexp ="[A-HJ-NPR-Z0-9]{17}", message = "Enter valid Chasis number",groups = {Create.class,Update.class})
	private String bikeChasisNumber;
	
	@NotBlank(message = "Known Issues cannot be empty or just whitespace",groups = Create.class)
	private String knownIssues;
	
	@NotNull(message = "Cost cannot be empty or just whitespace",groups = Create.class)
	private double cost;
	
	@NotNull(message = "Given Date cannot be empty or just whitespace",groups = Create.class)
	private LocalDateTime givenDate;
	
	@NotNull(message = "Expected Delivery Date cannot be empty or just whitespace",groups = Create.class)
	private LocalDate expectedDeliveryDate;
	
	private LocalDateTime createdDateTime;
	private LocalDateTime updateDateTime;
	
	@Valid
	private CustomerDto customerDto;

}
