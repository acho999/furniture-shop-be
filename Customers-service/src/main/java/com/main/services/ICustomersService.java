package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.CustomerDTO;


public interface ICustomersService extends UserDetailsService{
	
    public CompletableFuture<CustomerDTO> createCustomer(CustomerDTO customer);
	
	public CompletableFuture<CustomerDTO> getByUsername(String userName);
	
	public CompletableFuture<CustomerDTO> update(CustomerDTO customer);
	
	public void delete(String id);
	
	public CompletableFuture<CustomerDTO> getCustomerDetails(String id);
	
	public CompletableFuture<List<CustomerDTO>> getCustomers();


}
