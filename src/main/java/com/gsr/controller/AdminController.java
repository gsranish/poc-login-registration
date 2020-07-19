package com.gsr.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gsr.dto.UserRegistrationDto;
import com.gsr.entity.User;
import com.gsr.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	private static final Logger LOGGER = LogManager.getLogger(AdminController.class.getName());
	
	@GetMapping("/")
	public String getAdmin() {
		LOGGER.info("Admin page is started");
		return "Admin page is started";
	}

	@PostMapping("/add")
	public ResponseEntity<?> addAdmin(@ModelAttribute("user") @Valid UserRegistrationDto userDto,BindingResult result){
		LOGGER.info("addAdmin method started");
		ResponseEntity<?> resp=null;
		User existing = userService.findByEmail(userDto.getEmail());
		if (existing != null) {
			resp=new ResponseEntity<String>("There is already an an account registered with that email", HttpStatus.INTERNAL_SERVER_ERROR);
			LOGGER.info(resp.toString());
			result.rejectValue("email", null, "There is already an account registered with that email");
		}
		resp=new ResponseEntity<String>("An account registered with that email", HttpStatus.OK);
		userService.save(userDto);
		return resp;
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> getAllUsers(){
		LOGGER.info("getAllUsers method started");
		ResponseEntity<?> resp=null;
		try {
			List<User> users=userService.getAllUsers();
			resp=new ResponseEntity<String>("Users : "+users,HttpStatus.OK);
		}catch(Exception e) {
			LOGGER.info("exception occurred in getAllUsers method started");
			resp=new ResponseEntity<String>("Exception in fetching Users ",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}
}
