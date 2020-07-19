package com.gsr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PocLoginRegistrationApplication {

	
	private static final Logger LOGGER = LogManager.getLogger(PocLoginRegistrationApplication.class.getName());

	public static void main(String[] args) {
		
		LOGGER.info("Poc Login Registration Application application started ");
		SpringApplication.run(PocLoginRegistrationApplication.class, args);
	}

}
