package com.main.repositories;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "customers-service")
public interface CustomersServiceClient {
	
	@PostMapping(value = "/customers/create")
	public void createCustomer();

}
