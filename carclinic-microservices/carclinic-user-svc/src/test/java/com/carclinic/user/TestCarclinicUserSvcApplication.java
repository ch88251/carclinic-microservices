package com.carclinic.user;

import org.springframework.boot.SpringApplication;

public class TestCarclinicUserSvcApplication {

	public static void main(String[] args) {
		SpringApplication.from(CarclinicUserSvcApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
