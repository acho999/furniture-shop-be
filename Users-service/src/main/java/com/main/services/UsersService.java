package com.main.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.UserDto;


public interface UsersService extends UserDetailsService {
	
	public UserDto createUser(UserDto user);
	
	public UserDto getByUsername(String userName);
	
	public void update(UserDto user);
	
	public void delete(long id);
	
	public UserDto getUserDetails(long id);

}
