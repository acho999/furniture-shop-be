package com.main.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.main.repositories.CustomersServiceClient;

import feign.FeignException;

@Component
public class CustomersServiceFallback implements CustomersServiceClient{
	
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
