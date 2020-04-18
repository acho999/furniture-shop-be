package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.UserDto;


public interface UsersService extends UserDetailsService{
	
	public CompletableFuture<UserDto> createUser(UserDto user);
	
	public CompletableFuture<UserDto> getByUsername(String userName);
	
	public CompletableFuture<UserDto> update(UserDto user);
	
	public boolean delete(String id);
	
	public CompletableFuture<UserDto> getUserDetails(String id);
	
	public CompletableFuture<List<UserDto>> getUsers();

}
