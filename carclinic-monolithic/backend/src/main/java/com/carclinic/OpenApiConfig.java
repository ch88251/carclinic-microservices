package com.carclinic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	@Bean
	public OpenAPI vehicleDatabaseOpenAPI() {
		return new OpenAPI().info(new Info().title("Car Clinic REST API").description("Backend API for Car Clinic").version("1.0"));
	}
}