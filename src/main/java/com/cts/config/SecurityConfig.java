package com.cts.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cts.security.JwtAccessDeniedHandler;
import com.cts.security.JwtAuthenticationEntryPoint;
import com.cts.security.JwtAuthenticationFilter;



// Configures Spring Security for the application.

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	// Sets up security configurations for access control
    SecurityConfig(JwtAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }
    
    
    // Defines the security filter chain for HTTP requests.
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(config->config.disable());
		
		http.authorizeHttpRequests(auth->auth.requestMatchers("/api/auth/**","/swagger-ui/**",
				"/swagger-ui.html", "/v3/api-docs/**").permitAll()
				.anyRequest().authenticated())
				.exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler))
				.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	
	// 	Provides the authentication manager bean.
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		
		return config.getAuthenticationManager();
	}
	
	
	//Provides the password encoder bean using BCrypt.
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
