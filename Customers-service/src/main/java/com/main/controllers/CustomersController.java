package com.main.controllers;

import javax.transaction.Transactional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequestMapping(value = "/customers")
public class CustomersController {
	
	

}
