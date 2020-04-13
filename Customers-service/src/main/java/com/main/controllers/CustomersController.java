package com.main.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.DTO.CustomerDTO;
import com.main.models.LoginRequestModel;
import com.main.services.CustomersServiceImplementation;

@RestController
@Transactional
@RequestMapping(value = "/customers")
public class CustomersController {

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private CustomersServiceImplementation service;

	@PostMapping(value = "/create", produces = { MediaType.APPLICATION_JSON_VALUE,
			                                     MediaType.APPLICATION_XML_VALUE },
			                        consumes = { MediaType.APPLICATION_XML_VALUE,
					                             MediaType.APPLICATION_JSON_VALUE })
	public CompletableFuture<ResponseEntity<CustomerDTO>> createUser(@Valid @RequestBody CustomerDTO customer)
			throws ParseException, InterruptedException, ExecutionException {

		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		CustomerDTO custDto = mapper.map(customer, CustomerDTO.class);

		CompletableFuture<CustomerDTO> future = new CompletableFuture<CustomerDTO>();

		future.complete(this.service.createCustomer(custDto).get());

		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));

	}

	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello customer";
	}

	@PostMapping(value = "/login", consumes = { MediaType.APPLICATION_XML_VALUE,
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity<UserDetails> login(@Valid @RequestBody LoginRequestModel login) {

		UserDetails currentCustPrincipal = this.service.loadUserByUsername(login.getUsername());

		return ResponseEntity.status(HttpStatus.OK).body(currentCustPrincipal);

	}

	@GetMapping(value = "/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<CustomerDTO>> adminDetails(@PathVariable String id)
			throws InterruptedException, ExecutionException {

		CompletableFuture<CustomerDTO> future = new CompletableFuture<CustomerDTO>();

		future.complete(this.service.getCustomerDetails(id).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<CustomerDTO>>> getAll() throws InterruptedException, ExecutionException {

		CompletableFuture<List<CustomerDTO>> future = new CompletableFuture<List<CustomerDTO>>();

		future.complete(this.service.getCustomers().get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<CustomerDTO>> update(@Valid @RequestBody CustomerDTO admin)
			throws InterruptedException, ExecutionException {

		CompletableFuture<CustomerDTO> future = new CompletableFuture<CustomerDTO>();

		future.complete(this.service.update(admin).get());

		return future.thenApply(result -> ResponseEntity.ok().body(result));

	}

	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws InterruptedException, ExecutionException {

		this.service.delete(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

	}

}
