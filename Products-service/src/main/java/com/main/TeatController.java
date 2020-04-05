package com.main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeatController {
	
	@RequestMapping(value = "/",method = RequestMethod.GET)
	public String get() {
		return "Hello";
	}

}

