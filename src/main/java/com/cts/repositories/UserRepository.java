package com.cts.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.entities.User;


// Repository interface for User entity.
// Provides CRUD operations and custom query methods for user data.

public interface UserRepository extends JpaRepository<User, Integer>{
	
	// Finds a user by either username or email
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	
	
	// Checks if a user exists with the given username or email
	
	boolean existsByUsernameOrEmail(String username,String email);

}
