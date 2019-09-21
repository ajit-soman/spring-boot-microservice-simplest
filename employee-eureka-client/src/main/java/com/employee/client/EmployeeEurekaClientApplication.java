package com.employee.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class EmployeeEurekaClientApplication {
	@Value("${number}")
	String number;
	
	public static void main(String[] args) {
		SpringApplication.run(EmployeeEurekaClientApplication.class, args);
	}
	
	@GetMapping("/")
	String sayHello() {
		return "Hello from Employee microservice "+number;
	}

}
