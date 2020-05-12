package com.main.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.DTO.ProductDTO;
import com.main.services.IProductsService;

@RestController
@Transactional
@RequestMapping(value = "/products")
public class ProductsController {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IProductsService service;
	

	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello order";
	}

	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },
			                        consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	public CompletableFuture<ResponseEntity<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO product)
			throws ParseException, InterruptedException, ExecutionException {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		ProductDTO productDto = mapper.map(product, ProductDTO.class);

		CompletableFuture<ProductDTO> future = new CompletableFuture<ProductDTO>();

		future.complete(this.service.createProduct(productDto).get());

		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));

	}

	@GetMapping(value = "/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<ProductDTO>> productDetails(@PathVariable String id)
			throws InterruptedException, ExecutionException {

		CompletableFuture<ProductDTO> future = new CompletableFuture<ProductDTO>();

		future.complete(this.service.getProductDetails(id).get());
		
		if (future.get() != null) {
			return future.thenApply(result -> ResponseEntity.ok().body(result));
		}

		return future.thenApply(result -> ResponseEntity.notFound().build());

	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<ProductDTO>>> getAll() throws InterruptedException, ExecutionException {

		CompletableFuture<List<ProductDTO>> future = new CompletableFuture<List<ProductDTO>>();

		future.complete(this.service.getProducts().get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<ProductDTO>> update(@Valid @RequestBody ProductDTO product)
			throws InterruptedException, ExecutionException {

		CompletableFuture<ProductDTO> future = new CompletableFuture<ProductDTO>();

		future.complete(this.service.update(product).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws InterruptedException, ExecutionException {

		this.service.delete(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}



}
