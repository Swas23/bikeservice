package com.cts.service;

import com.cts.dtos.LoginDto;

// Service interface for authentication-related operations.

public interface AuthService {

	String login(LoginDto loginDto);
}
