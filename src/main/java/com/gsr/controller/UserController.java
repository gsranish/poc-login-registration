package com.gsr.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {
	
	private static final Logger LOGGER = LogManager.getLogger(UserController.class.getName());
	
	@GetMapping("/")
    public String root() {
		LOGGER.info("Home page started");
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
    	LOGGER.info("Login page started");
        return "login";
    }

    @GetMapping("/user")
    public String userIndex() {
    	LOGGER.info("User page started");
        return "user/index";
    }

}
