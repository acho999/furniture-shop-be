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

import com.main.DTO.SaleDTO;
import com.main.services.ISalesService;

@RestController
@Transactional
@RequestMapping(value = "/sales")
public class SalesController {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private ISalesService service;

	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello sale";
	}

	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },
			                        consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	public CompletableFuture<ResponseEntity<SaleDTO>> createSale(@Valid @RequestBody SaleDTO sale)
			throws ParseException, InterruptedException, ExecutionException {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		SaleDTO saleDto = mapper.map(sale, SaleDTO.class);

		CompletableFuture<SaleDTO> future = new CompletableFuture<SaleDTO>();

		future.complete(this.service.createSale(saleDto).get());

		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));

	}

	@GetMapping(value = "/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<SaleDTO>> saleDetails(@PathVariable String id)
			throws InterruptedException, ExecutionException {

		CompletableFuture<SaleDTO> future = new CompletableFuture<SaleDTO>();

		future.complete(this.service.getSaleDetails(id).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<SaleDTO>>> getAll() throws InterruptedException, ExecutionException {

		CompletableFuture<List<SaleDTO>> future = new CompletableFuture<List<SaleDTO>>();

		future.complete(this.service.getSales().get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}
	
	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<SaleDTO>>> getAllForCurrentUser(String userId) throws InterruptedException, ExecutionException {

		CompletableFuture<List<SaleDTO>> future = new CompletableFuture<List<SaleDTO>>();

		future.complete(this.service.getSalesForCurrentUser(userId).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<SaleDTO>> update(@Valid @RequestBody SaleDTO sale)
			throws InterruptedException, ExecutionException {

		CompletableFuture<SaleDTO> future = new CompletableFuture<SaleDTO>();

		future.complete(this.service.update(sale).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws InterruptedException, ExecutionException {

		this.service.delete(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

}
