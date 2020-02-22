package com.miniproject.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.miniproject.util.LogUtil;

/**
 * @author muhilkennedy
 *
 * Starting point of the spring-boot microservice.
 */

@SpringBootApplication
@EntityScan(basePackages = { "com.miniproject" })
@ComponentScan({"com.miniproject"})
@EnableJpaRepositories("com.miniproject.repository")
public class ProjectApplication {
	public static void main(String[] args) {
		LogUtil.getLogger(ProjectApplication.class).debug("Starting Project Application");
		SpringApplication.run(ProjectApplication.class, args);
	}

}
