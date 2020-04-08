package com.main.controllers;


import java.util.concurrent.CompletableFuture;
import org.springframework.http.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.DTO.CreateCategoryDTO;
import com.main.models.Category;
import com.main.services.ICategoriesService;

@RestController
@Transactional(readOnly = true)
@RequestMapping(path = "/categories")
public class CategoriesController {
	
	@Autowired
	private ICategoriesService categoryService;
	
	@RequestMapping(path = "/create", 
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<Category>> create(CreateCategoryDTO category){
		
		return null;
		
	}
}
