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

import com.main.DTO.OrderDTO;
import com.main.services.IOrdersService;

@RestController
@Transactional
@RequestMapping(value = "/orders")
public class OrdersController {
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private IOrdersService service;
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello order";
	}

	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE },
			                        consumes = { MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE })
	public CompletableFuture<ResponseEntity<OrderDTO>> createOrder(@Valid @RequestBody OrderDTO order)
			throws ParseException, InterruptedException, ExecutionException {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		OrderDTO orderDto = mapper.map(order, OrderDTO.class);

		CompletableFuture<OrderDTO> future = new CompletableFuture<OrderDTO>();

		future.complete(this.service.createOrder(orderDto).get());

		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));

	}

	@GetMapping(value = "/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<OrderDTO>> orderDetails(@PathVariable String id)
			throws InterruptedException, ExecutionException {

		CompletableFuture<OrderDTO> future = new CompletableFuture<OrderDTO>();

		future.complete(this.service.getOrderDetails(id).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<OrderDTO>>> getAll() throws InterruptedException, ExecutionException {

		CompletableFuture<List<OrderDTO>> future = new CompletableFuture<List<OrderDTO>>();

		future.complete(this.service.getOrders().get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<OrderDTO>> update(@Valid @RequestBody OrderDTO order)
			throws InterruptedException, ExecutionException {

		CompletableFuture<OrderDTO> future = new CompletableFuture<OrderDTO>();

		future.complete(this.service.update(order).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws InterruptedException, ExecutionException {

		this.service.delete(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}


}
