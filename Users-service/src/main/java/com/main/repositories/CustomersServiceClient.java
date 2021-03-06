package com.main.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.main.DTO.CustomerDTO;

import feign.FeignException;
import feign.Param;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "customers-service",fallbackFactory = CustomersServiceFallbackFactory.class)
public interface CustomersServiceClient {
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/customers/create", consumes = "application/json",headers = {"client=users"})
	public ResponseEntity<CustomerDTO> createCustomer(@RequestParam String username);

}

@Component
class CustomersServiceFallbackFactory implements FallbackFactory<CustomersServiceClient>{

	@Override
	public CustomersServiceClient create(Throwable cause) {
		// TODO Auto-generated method stub
		return new CustomersServiceFallback(cause);
	}

}

class CustomersServiceFallback implements CustomersServiceClient{
	
	private final Throwable cause;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public CustomersServiceFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public ResponseEntity<CustomerDTO> createCustomer(String username) {
		
		if (this.cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			
			this.logger.error("404 error took place when call createCustomer" + this.cause.getLocalizedMessage());
			
		} else {
			 this.logger.error("Other error took place" + this.cause.getLocalizedMessage());
		}
		
		return ResponseEntity.ok().body(new CustomerDTO());
		
	}
	
}



