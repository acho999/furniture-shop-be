package com.main.services;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.CustomerDTO;
import com.main.models.Admin;
import com.main.models.Customer;
import com.main.models.CustomerPrincipal;
import com.main.repositories.CustomersRepository;

@Service
@Transactional(readOnly = true)
public class CustomersServiceImplementation implements ICustomersService{
	
	@Autowired
	private CustomersRepository repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			
			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				
			CompletableFuture<CustomerDTO> dto = this.getByUsername(username);
			
			Customer entity = mapper.map(dto.get(), Customer.class);
			
			CustomerPrincipal userPrincipal = new CustomerPrincipal(entity);
			
			return userPrincipal;
			
			} catch (Exception e) {
				System.out.println(e.getMessage());
				System.out.println(e.getStackTrace());
			}
			
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> createCustomer(CustomerDTO customer) {
		
		

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> getByUsername(String userName) {

		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Optional<Customer> userEntity = repo.findAll().stream().filter(x->x.getUsername().equals(userName)).findFirst();
		
		CustomerDTO dtoToReturn = mapper.map(userEntity.get(), CustomerDTO.class);
		
		return CompletableFuture.completedFuture(dtoToReturn);
		
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> update(CustomerDTO customer) {

		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> getCustomerDetails(String id) {

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<List<CustomerDTO>> getCustomers() {

		return null;
	}
	

}
