package com.cts.service;

import java.util.List;

//import com.cts.dtos.BikeCreateDto;
import com.cts.dtos.BikeDto;
import com.cts.entities.Bike;


// Service interface for managing bike-related operations.
// Provides methods for retrieving, adding, updating, and deleting bikes.

public interface BikeService {
	
	//Retrieves a list of all bikes.
	List<BikeDto> getAll();
	
	// Retrieves a bike by its unique identifier.
	BikeDto getById(long id);
	
	// Adds a new bike to the system.
	Bike addBike(BikeDto bikeDto);
	
	// Updates an existing bike with the specified ID.
	Bike updateBike(long id,BikeDto bikeDto);
	
	// Deletes the bike with the specified ID
	void deleteBike(long id);

}
