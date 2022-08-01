package com.test.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.test.**" })
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@EnableConfigurationProperties
public class MyTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyTestApplication.class, args);
		System.out.println("added print statement");
		System.out.println("added test statement");
		//added Commented code here for sonarqube
	}
}
