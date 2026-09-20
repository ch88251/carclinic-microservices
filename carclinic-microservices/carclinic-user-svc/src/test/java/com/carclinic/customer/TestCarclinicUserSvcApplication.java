package com.carclinic.customer;

import org.springframework.boot.SpringApplication;

import com.carclinic.customer.CustomerServiceApplication;

public class TestCarclinicUserSvcApplication {

	public static void main(String[] args) {
		SpringApplication.from(CustomerServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
