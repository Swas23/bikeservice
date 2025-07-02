package com.cts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.dtos.BikeDto;
import com.cts.entities.Bike;
import com.cts.service.BikeService;
import com.cts.validation.Create;
import com.cts.validation.Update;

import jakarta.validation.Valid;


// REST controller for managing bike-related operations.
// Provides endpoints to create, read, update, and delete bikes.

@RestController
@RequestMapping("/api/bikes")
@EnableMethodSecurity
public class BikeController {
	
	@Autowired
	private BikeService bikeService;
	
	
	// Retrieves a list of all bikes
	// Accessible to all authenticated users.

	@GetMapping()
	public List<BikeDto> getAll(){
		return bikeService.getAll();
	}
	
	
	// Retrieves a specific bike by its ID.
	//Accessible to all authenticated users.

	@GetMapping("/{id}")
	public BikeDto getById(@PathVariable long id) {
		return bikeService.getById(id);
	}
	
	
	// Adds a new bike to the system.
	// Only users with ADMIN role can access this endpoint.
	// @return ResponseEntity with the created Bike and HTTP status 201.

	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Bike> addBike(@Validated(Create.class) @RequestBody BikeDto bikeDto){
		
		Bike bike = bikeService.addBike(bikeDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(bike);
	}
	
	
	// Updates an existing bike identified by its ID.
	// Only users with ADMIN role can access this endpoint.
	// @return ResponseEntity with the updated Bike and HTTP status 201

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Bike> updateBike(@PathVariable long id ,@Validated(Update.class) @RequestBody BikeDto bikeDto){
		Bike bike = bikeService.updateBike(id, bikeDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(bike);
	}
	
	
	// Deletes a bike from the system by its ID.
	// Only users with ADMIN role can access this endpoint.
	// @return ResponseEntity with HTTP status 202 if deletion is successful.

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Bike> deleteBike(@PathVariable long id){
		bikeService.deleteBike(id);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
}
