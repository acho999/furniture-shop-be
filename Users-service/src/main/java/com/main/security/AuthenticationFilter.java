package com.main.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.main.models.LoginRequestModel;
import com.main.models.AdminPrincipal;
import com.main.services.AdminsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	//private UserDetailsService usersService;
	private AdminsService service;
	private String tokenSecret;
	private Long tokenExpirationTime;
	private Environment env;

	
	public AuthenticationFilter(Environment env, AdminsService service, AuthenticationManager manager) {
		this.env = env;
		//this.usersService = usersService;
		super.setAuthenticationManager(manager);
		this.service = service;
		this.tokenExpirationTime = Long.parseLong(this.env.getProperty("token.expiration_time"));
		this.tokenSecret = this.env.getProperty("token.secret");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			LoginRequestModel credentials = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestModel.class);

			return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					credentials.getUsername(), credentials.getPassword(), new ArrayList<>()));

		} catch (IOException e) {

			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) {
		
		String userId = "";
		try {

			userId = String.valueOf(this.service.getByUsername(((AdminPrincipal) auth.getPrincipal())
					.getUsername())
					.get()
					.getId());

		

		Claims claims = Jwts.claims().setSubject(String.valueOf(userId));

		claims.put("roles", ((AdminPrincipal) auth.getPrincipal()).getAuthorities());

		Date nowDate = new Date();

		Date validity = new Date(System.currentTimeMillis() + tokenExpirationTime);

		String tokenString = Jwts.builder().setClaims(claims).setIssuedAt(nowDate).setExpiration(validity)
				.signWith(SignatureAlgorithm.HS512, this.tokenSecret).compact();

		response.setHeader("token", tokenString);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}

	}

}
