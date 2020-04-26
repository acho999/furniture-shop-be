package com.main.controllers;

import java.util.List;
import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.DTO.CreateUserDto;
import com.main.DTO.UserDto;
import com.main.models.LoginRequestModel;
import com.main.services.UsersService;


@RestController
@Transactional
@RequestMapping(value = "/users")
public class UsersController{
	
	
	private ModelMapper mapper;
	private UsersService usersService;
	private Environment env;
	
	@Autowired
	public UsersController(UsersService usersService, ModelMapper mapper,Environment env) {
		this.mapper = mapper;
		this.usersService = usersService;
		this.env = env;
	}
	
	@PostMapping(value = "/create",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			                       consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public CompletableFuture<ResponseEntity<UserDto>> createUser(@Valid @RequestBody CreateUserDto user) throws ParseException, InterruptedException, ExecutionException{
		
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(user, UserDto.class);
		
		CompletableFuture<UserDto> future = new CompletableFuture<UserDto>();
		
		//Future<UserDto> returnDto = this.userService.createUser(userDto);
		
		//return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
		
		future.complete(this.usersService.createUser(userDto).get());
		
		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));
		
	}
	
	@GetMapping(value = "/hello")
	public String hello() {
		return this.env.getProperty("gateway.ip") + this.env.getProperty("token.secret");
	}
	
	@PostMapping(value = "/login",consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			                      produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserDetails> login(@Valid @RequestBody LoginRequestModel login) {
		
		UserDetails currentUserPrincipal = this.usersService.loadUserByUsername(login.getUsername());
		
		return ResponseEntity.status(HttpStatus.OK).body(currentUserPrincipal);
		
	}
	
	@GetMapping(value = "/details/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<UserDto>> userDetails(@PathVariable String id) throws InterruptedException, ExecutionException{
		
		CompletableFuture<UserDto> future = new CompletableFuture<UserDto>();
		
		future.complete(this.usersService.getUserDetails(id).get());
		
		return future.thenApply(result -> ResponseEntity.ok().body(result));
		
	}
	
	@GetMapping(value = "/getAll",produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<List<UserDto>>> getAll() throws InterruptedException, ExecutionException{
		
		CompletableFuture<List<UserDto>> future = new CompletableFuture<List<UserDto>>();
		
		future.complete(this.usersService.getUsers().get());
		
		return future.thenApply(result -> ResponseEntity.ok().body(result));
		
	}
	
	@PostMapping(value = "/update",produces = MediaType.APPLICATION_JSON_VALUE,
			                       consumes = MediaType.APPLICATION_JSON_VALUE)
	public CompletableFuture<ResponseEntity<UserDto>> update(@Valid @RequestBody UserDto user) throws InterruptedException, ExecutionException{
		
		CompletableFuture<UserDto> future = new CompletableFuture<UserDto>();
		
		future.complete(this.usersService.update(user).get());
		
		return future.thenApply(result -> ResponseEntity.ok().body(result));
		
	}
	
	@PostMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) throws InterruptedException, ExecutionException{
		
		this.usersService.delete(id);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
	}
	
	

}
