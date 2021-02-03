package com.comercial.obras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ObrasApplication extends SpringBootServletInitializer{

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	
		return builder.sources(ObrasApplication.class);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ObrasApplication.class, args);
	}


}
