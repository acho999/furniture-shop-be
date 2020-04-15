package com.main.services;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.main.DTO.CustomerDTO;
import com.main.models.Customer;
import com.main.models.CustomerPrincipal;
import com.main.models.Role;
import com.main.repositories.CustomersRepository;
import com.main.repositories.RolesRepository;

@Service
@Transactional(readOnly = true)
public class CustomersServiceImplementation implements CustomersService {

	@Autowired
	private CustomersRepository repo;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private RolesRepository rolesRepo;

	@Autowired
	private BCryptPasswordEncoder encoder;

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
		try {
			Role role = null;

			this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

			Customer cust = mapper.map(customer, Customer.class);

			String encPassword = encoder.encode(customer.getPassword());

			cust.setEncryptedPassword(encPassword);

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			String dateString = format.format(new Date());

			Date date = format.parse(dateString);

			cust.setDate_created(date);

			role = this.rolesRepo.findById(2L).get();

			cust.setRole(role);

			repo.saveAndFlush(cust);

			CustomerDTO returnDto = mapper.map(cust, CustomerDTO.class);

			return CompletableFuture.completedFuture(returnDto);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return null;

	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> getByUsername(String userName) {

		this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Optional<Customer> userEntity = repo.findAll().stream().filter(x -> x.getUsername().equals(userName))
				.findFirst();

		CustomerDTO dtoToReturn = mapper.map(userEntity.get(), CustomerDTO.class);

		return CompletableFuture.completedFuture(dtoToReturn);

	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> update(CustomerDTO customer) {
		
this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Customer cust = null; 
		CustomerDTO returnObject = null;
		
		try {
			
			cust = this.repo.findById(customer.getId()).get();
			
			Role role = cust.getRole();
			
		    this.mapper.map(customer, cust);
		    
		    cust.role = role;
		    
		    returnObject = new CustomerDTO();
		    
		    this.mapper.map(cust, returnObject);
		    
		    this.repo.saveAndFlush(cust);
		    
		    return CompletableFuture.completedFuture(returnObject);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return CompletableFuture.completedFuture(returnObject);

	}

	@Override
	@Transactional(readOnly = false)
	public boolean delete(String id) {
		
		Optional<Customer> customerOptional = null;
		
		try {
			this.repo.deleteById(id);
			
			customerOptional = this.repo.findById(id);
			
			if(customerOptional.get() != null) {
				return false;
			}
			
			return true;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
		return true;
		
	}
	
	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<CustomerDTO> getCustomerDetails(String id) {
		
		CustomerDTO customerDetails = null;
		Customer cust = null;
		try {
			
			cust = this.repo.findById(id).get();
			
			if (cust != null) {
				
				customerDetails = this.mapper.map(cust, CustomerDTO.class);
				
				return CompletableFuture.completedFuture(customerDetails);
				
			}
		
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

		return null;
	}

	@Override
	@Transactional(readOnly = false)
	@Async("asyncExecutor")
	public CompletableFuture<List<CustomerDTO>> getCustomers() {
		
		List<CustomerDTO> customerDetails = new ArrayList<CustomerDTO>();
		Type listType = new TypeToken<List<CustomerDTO>>() {}.getType();
		
		try {
		
			customerDetails = this.mapper.map(this.repo.findAll(),listType);
			
			
			return CompletableFuture.completedFuture(customerDetails);
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		return null;

	}

}
