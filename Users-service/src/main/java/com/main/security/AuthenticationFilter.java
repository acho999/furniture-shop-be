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
import com.main.models.UserPrincipal;
import com.main.services.UsersService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private UsersService service;
	private String tokenSecret;
	private Long tokenExpirationTime;
	private Environment env;

	
	public AuthenticationFilter(Environment env, UsersService service, AuthenticationManager manager) {
		this.env = env;
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
		String userName = "";
		String userId = "";
		try {

			/*userId = String.valueOf(this.service.getByUsername(((UserPrincipal) auth.getPrincipal())
					.getUsername())
					.get()
					.getId());*/
			
			
            userName = ((UserPrincipal) auth.getPrincipal()).getUsername();
		

		Claims claims = Jwts.claims().setSubject(String.valueOf(userName));

		claims.put("roles", ((UserPrincipal) auth.getPrincipal()).getAuthorities());

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
