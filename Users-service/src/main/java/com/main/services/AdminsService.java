package com.main.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.UserDto;


public interface AdminsService extends UserDetailsService {
	
	public CompletableFuture<UserDto> createUser(UserDto user);
	
	public CompletableFuture<UserDto> getByUsername(String userName);
	
	public boolean update(UserDto user);
	
	public boolean delete(String id);
	
	public CompletableFuture<UserDto> getUserDetails(String id);

}
