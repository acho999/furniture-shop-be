package com.main.configuration;

import org.springframework.stereotype.Component;

import com.main.repositories.CustomersServiceClient;

import feign.hystrix.FallbackFactory;

@Component
public class CustomersServiceFallbackFactory implements FallbackFactory<CustomersServiceClient>{

	@Override
	public CustomersServiceClient create(Throwable cause) {
		// TODO Auto-generated method stub
		return new CustomersServiceFallback(cause);
	}

}
