package com.cts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.Role;


// Repository interface for Role entity.
// Provides CRUD operations and custom query methods for roles

public interface RoleRepository extends JpaRepository<Role, Integer>{
	

	// Finds a role by its name

	Optional<Role> findByName(String name);
	
	
	// Checks if a role with the given name exists
	
	boolean existsByName(String name);

}
