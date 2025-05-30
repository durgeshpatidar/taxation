package com.noovosoft.salaried;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SalariedServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalariedServiceApplication.class, args);
	}

}
