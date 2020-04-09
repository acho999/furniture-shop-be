package com.main.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.CustomerDTO;
import com.main.repositories.CustomerRepository;

@Transactional(readOnly = true)
public class CustomersService implements ICustomersService{
	
	@Autowired
	private CustomerRepository repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	@Transactional(readOnly = false)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public CustomerDTO createUser(CustomerDTO customer) {

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public CustomerDTO getByUsername(String userName) {

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public void update(CustomerDTO customer) {
		
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(String id) {
		
	}

	@Override
	@Transactional(readOnly = false)
	public CustomerDTO getUserDetails(String id) {
		return null;
	}

}
