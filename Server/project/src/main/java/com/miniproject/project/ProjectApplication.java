package com.miniproject.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author muhilkennedy
 *
 * Starting point of the microservice.
 */

@SpringBootApplication
@ComponentScan("com.miniproject.controller")
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

}
