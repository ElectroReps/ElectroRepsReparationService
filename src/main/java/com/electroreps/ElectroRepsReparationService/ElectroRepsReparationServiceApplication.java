package com.electroreps.ElectroRepsReparationService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ElectroRepsReparationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectroRepsReparationServiceApplication.class, args);
	}

}
