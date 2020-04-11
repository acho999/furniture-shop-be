package com.main.controllers;

import java.text.ParseException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.main.DTO.CreateAdminDto;
import com.main.DTO.AdminDto;
import com.main.models.LoginRequestModel;
import com.main.services.AdminsService;


@RestController
@Transactional
@RequestMapping(value = "/admins")
public class UsersController{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private AdminsService userService;
	
	@PostMapping(value = "/create",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			                       consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public CompletableFuture<ResponseEntity<AdminDto>> createUser(@Valid @RequestBody CreateAdminDto user) throws ParseException, InterruptedException, ExecutionException{
		
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		AdminDto userDto = mapper.map(user, AdminDto.class);
		
		CompletableFuture<AdminDto> future = new CompletableFuture<AdminDto>();
		
		//Future<UserDto> returnDto = this.userService.createUser(userDto);
		
		//return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
		
		future.complete(this.userService.createUser(userDto).get());
		
		return future.thenApply(x -> ResponseEntity.status(HttpStatus.CREATED).body(x));
		
	}
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hello";
	}
	
	@PostMapping(value = "/login",consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE},
			                      produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	public ResponseEntity<UserDetails> login(@Valid @RequestBody LoginRequestModel login) {
		
		UserDetails currentUserPrincipal = this.userService.loadUserByUsername(login.getUsername());
		
		return ResponseEntity.status(HttpStatus.OK).body(currentUserPrincipal);
		
	}

}
