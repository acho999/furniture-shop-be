package com.main.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.main.DTO.CustomerDTO;


public interface CustomersService {
	
    public CompletableFuture<CustomerDTO> createCustomer(String userName);
	
	public CompletableFuture<CustomerDTO> update(CustomerDTO customer);
	
	public boolean delete(String id);
	
	public CompletableFuture<CustomerDTO> getCustomerDetails(String id);
	
	public CompletableFuture<List<CustomerDTO>> getCustomers();


}
