package com.miniproject.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author muhilkennedy
 *
 */
@RestController
public class BaseController {

	@RequestMapping("/")
    public String index() {
        return "Lets get started with Spring Boot!";
    }
	
}
