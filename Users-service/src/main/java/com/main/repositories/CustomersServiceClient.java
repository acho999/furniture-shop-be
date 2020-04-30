package com.main.repositories;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;


import feign.FeignException;
import feign.hystrix.FallbackFactory;

@FeignClient(name = "customers-service",fallback = CustomersServiceFallback.class)
public interface CustomersServiceClient {
	
	@PostMapping(value = "/customers/create")
	public void createCustomer();

}

@Component
class CustomersServiceFallbackFactory implements FallbackFactory<CustomersServiceClient>{

	@Override
	public CustomersServiceClient create(Throwable cause) {
		// TODO Auto-generated method stub
		return new CustomersServiceFallback(cause);
	}

}

@Component
class CustomersServiceFallback implements CustomersServiceClient{
	
	private final Throwable cause;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public CustomersServiceFallback(Throwable cause) {
		this.cause = cause;
	}

	@Override
	public void createCustomer() {
		
		if (this.cause instanceof FeignException && ((FeignException) cause).status() == 404) {
			
			this.logger.error("404 error took place when call createCustomer" + this.cause.getLocalizedMessage());
			
		} else {
			 this.logger.error("Other error took place" + this.cause.getLocalizedMessage());
		}
		
	}
	
}



