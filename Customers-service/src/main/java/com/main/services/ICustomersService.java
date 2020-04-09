package com.main.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.CustomerDTO;


public interface ICustomersService extends UserDetailsService{
	
    public CustomerDTO createUser(CustomerDTO customer);
	
	public CustomerDTO getByUsername(String userName);
	
	public void update(CustomerDTO customer);
	
	public void delete(String id);
	
	public CustomerDTO getUserDetails(String id);


}
