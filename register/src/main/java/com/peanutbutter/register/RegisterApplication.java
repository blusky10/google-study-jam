package com.peanutbutter.register;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RegisterApplication implements CommandLineRunner {

	static final Logger LOGGER = LoggerFactory.getLogger(RegisterApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(RegisterApplication.class, args);
	}

	@Value("${mail.service.url}")
	private String mailService;

	@Value("${register.service.url}")
	private String registerService;

	@Override
	public void run(String... args) throws Exception {

		LOGGER.info("mailService : " + mailService);
		LOGGER.info("registerService : " + registerService);

	}
}
