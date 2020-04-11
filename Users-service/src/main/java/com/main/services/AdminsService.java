package com.main.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.AdminDto;


public interface AdminsService extends UserDetailsService {
	
	public CompletableFuture<AdminDto> createUser(AdminDto user);
	
	public CompletableFuture<AdminDto> getByUsername(String userName);
	
	public CompletableFuture<AdminDto> update(AdminDto user);
	
	public boolean delete(String id);
	
	public CompletableFuture<AdminDto> getUserDetails(String id);

}
