package com.cts.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cts.entities.User;
import com.cts.repositories.UserRepository;


// Custom implementation of Spring Security's UserDetailsService.
// Loads user-specific data during authentication.

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	
	/**
     * Loads a user by username or email and converts it to Spring Security's UserDetails.
     *
     * @param username the username or email used to authenticate
     * @return UserDetails containing username, password, and authorities
     * @throws UsernameNotFoundException if user is not found
     */

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsernameOrEmail(username, username)
				.orElseThrow(()-> new UsernameNotFoundException("User not Found"));
		
		
		List<GrantedAuthority> authorities 
		 	= user.getRoles().stream().map(role->new SimpleGrantedAuthority(role.getName()))
		 	.collect(Collectors.toList());
		
		return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),authorities);
	}

}
