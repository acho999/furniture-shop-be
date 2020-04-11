package com.main;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"com.main.controllers","com.main.configuration",
		"com.main.repositories","com.main.services","com.main.security","com.main.models"})
//@EntityScan("com.main.models")
@EnableDiscoveryClient
public class UsersServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UsersServiceApplication.class, args);
		
	}
	
}
