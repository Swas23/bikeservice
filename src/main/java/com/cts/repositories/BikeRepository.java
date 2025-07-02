package com.cts.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Bike;


// Repository interface for Bike entity.
// Provides CRUD operations and query methods for Bike data.

public interface BikeRepository extends JpaRepository<Bike, Long>{

}
