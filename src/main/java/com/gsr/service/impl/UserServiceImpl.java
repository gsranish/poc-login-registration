package com.gsr.service.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gsr.dto.UserRegistrationDto;
import com.gsr.entity.Role;
import com.gsr.entity.User;
import com.gsr.repository.UserRepository;
import com.gsr.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	public User findByEmail(String email) {
		LOGGER.info("findByEmail method started");
		return userRepo.findByEmail(email);
	}

	@Transactional
	public User save(UserRegistrationDto registration) {
		LOGGER.info("save User method started from service Layer");
		User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail().toString());
        user.setPassword(pwdEncoder.encode(registration.getPassword()));
        user.setRoles(Arrays.asList(new Role("USER")));
        userRepo.save(user);
		return user;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LOGGER.info("loadUserByUsername method started");
		 User user = userRepo.findByEmail(username);
	        if (user == null) {
	            throw new UsernameNotFoundException("Invalid username or password.");
	        }
	        return new org.springframework.security.core.userdetails.User(user.getEmail(),
	            user.getPassword(),
	            mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream()
	            .map(role -> new SimpleGrantedAuthority(role.getName()))
	            .collect(Collectors.toList());
	}

	@Override
	public List<User> getAllUsers() {
		LOGGER.info("getAllUsers method started from ServiceImpl class");
		return userRepo.findAll();
	}
}
