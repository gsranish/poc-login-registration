package com.gsr.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.gsr.dto.UserRegistrationDto;
import com.gsr.entity.User;

public interface UserService extends UserDetailsService {
	
	public User findByEmail(String email);
	public User save(UserRegistrationDto registration);
	public List<User> getAllUsers();
	
}
