package com.main.exception.models;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder{

	@Override
	public Exception decode(String methodKey, Response response) {

		switch (response.status()) {
		case 404:
			
			break;

		default:
			if (methodKey.contains("createCustomer")) {
				return new ResponseStatusException(HttpStatus.valueOf(response.status()));
			}
			break;
		}
		return null;
	}

}
