package com.main.controllers;


import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.DTO.CategoryDTO;
import com.main.services.ICategoriesService;

@RestController
@Transactional(readOnly = true)
@RequestMapping(path = "/categories")
public class CategoriesController {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private ICategoriesService categoryService;
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello order";
	}

	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },
			                        consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	public CompletableFuture<ResponseEntity<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO product)
			throws ParseException, InterruptedException, ExecutionException {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		CategoryDTO productDto = mapper.map(product, CategoryDTO.class);

		CompletableFuture<CategoryDTO> future = new CompletableFuture<CategoryDTO>();

		future.complete(this.categoryService.createCategory(productDto).get());

		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));

	}

	@GetMapping(value = "/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<CategoryDTO>> categoryDetails(@PathVariable String id)
			throws InterruptedException, ExecutionException {

		CompletableFuture<CategoryDTO> future = new CompletableFuture<CategoryDTO>();

		future.complete(this.categoryService.getCategoryDetails(id).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<CategoryDTO>>> getAll() throws InterruptedException, ExecutionException {

		CompletableFuture<List<CategoryDTO>> future = new CompletableFuture<List<CategoryDTO>>();

		future.complete(this.categoryService.getCategories().get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE,
			                        consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<CategoryDTO>> update(@Valid @RequestBody CategoryDTO product)
			throws InterruptedException, ExecutionException {

		CompletableFuture<CategoryDTO> future = new CompletableFuture<CategoryDTO>();

		future.complete(this.categoryService.update(product).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws InterruptedException, ExecutionException {

		this.categoryService.delete(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

}
