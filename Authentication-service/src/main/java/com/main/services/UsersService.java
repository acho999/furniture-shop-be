package com.main.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.models.User;


public interface UsersService extends UserDetailsService{
	
	
	public CompletableFuture<User> getByUsername(String userName);
	

}
