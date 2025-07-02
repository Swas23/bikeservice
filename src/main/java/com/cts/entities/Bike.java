package com.cts.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


// Entity class representing a Bike.

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "bikes")
public class Bike {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long bikeId;
	private String bikeMake;
	private String modelName;
	private String bikeRegistrationNumber;
	private String bikeChasisNumber;
	private String knownIssues;
	private double cost;
	private LocalDateTime givenDate;
	private LocalDate expectedDeliveryDate;
	private LocalDateTime createdDateTime;
	private LocalDateTime updateDateTime;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "customerId")
	private Customer customer;
	
	

}
