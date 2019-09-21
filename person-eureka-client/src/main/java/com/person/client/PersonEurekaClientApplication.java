package com.person.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class PersonEurekaClientApplication {
	
	@Value("${number}")
	private String number;
	@Autowired
	private RestTemplate restTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(PersonEurekaClientApplication.class, args);
	}
	
	@Bean
	@LoadBalanced
	RestTemplate resTemplate() {
		return new RestTemplate();
	}
	
	@GetMapping("/")
	String sayHello() {
		return "Hello from Person microservice "+number;
	}
	
	@GetMapping("/employee")
	String getHelloFromEmployee() {
		String response = restTemplate.getForObject("http://employee-service/", String.class);
		return response;
	}

}
