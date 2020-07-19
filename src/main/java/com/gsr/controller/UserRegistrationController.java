package com.gsr.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gsr.dto.UserRegistrationDto;
import com.gsr.entity.User;
import com.gsr.service.UserService;

@Controller
@RequestMapping("/register")
public class UserRegistrationController {
	
	private static final Logger LOGGER = LogManager.getLogger(UserRegistrationController.class.getName());
	
	@Autowired
	private UserService service;
	
	@ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }
	
	@GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }
	
	@PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,BindingResult result) {
		LOGGER.info("registerUserAccount method started");
        User existing = service.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) { 		
        	return "registration";			
        	}
        	service.save(userDto);
        return "redirect:/registration?success";
    }
}
