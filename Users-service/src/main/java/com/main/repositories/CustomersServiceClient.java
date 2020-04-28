package com.main.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.main.configuration.CustomersServiceFallbackFactory;

@FeignClient(name = "customers-service",fallback = CustomersServiceFallbackFactory.class)
public interface CustomersServiceClient {
	
	@PostMapping(value = "/customers/create")
	public void createCustomer();

}
