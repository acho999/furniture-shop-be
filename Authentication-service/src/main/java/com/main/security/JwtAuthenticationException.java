package com.main.security;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException{

	private static final long serialVersionUID = -3301605592208950415L;

	public JwtAuthenticationException(String msg,Throwable t) {
		super(msg,t);
	}

	public JwtAuthenticationException(String msg) {
		super(msg);
	}
}
