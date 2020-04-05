package com.main.controllers;

import java.text.ParseException;

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

import com.main.DTO.CreateUserDto;
import com.main.DTO.UserDto;
import com.main.models.LoginRequestModel;
import com.main.services.UsersService;

@RestController
@Transactional
@RequestMapping(value = "/users")
public class UsersController{
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UsersService userService;
	
	@PostMapping(value = "/create",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE},
			                       consumes = {MediaType.APPLICATION_XML_VALUE,MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserDto user) throws ParseException{
		
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		UserDto userDto = mapper.map(user, UserDto.class);
		
		UserDto returnDto = this.userService.createUser(userDto);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(returnDto);
		
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
