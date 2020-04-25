package com.main.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



public class AuthorizaionFilter extends BasicAuthenticationFilter {

	
	public AuthorizaionFilter(AuthenticationManager manager) {
		super(manager);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String userName = req.getHeader("X-Zuul-UserId");
		
		String[] rolesString = req.getHeader("X-Zuul-UserRoles").split(" ");

		if (userName == null || rolesString == null) {//|| !authHeader.startsWith(this.headerPrefix)) {

			chain.doFilter(req, res);
			return;
		}
		
		List<GrantedAuthority> roles = new ArrayList<>();
		
		for (int i = 0; i < rolesString.length; i++) {
			
			roles.add(new SimpleGrantedAuthority(rolesString[i].toUpperCase()));//"ROLE_" + (roles1[i].toUpperCase())));
			// 
		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName,null, roles);

		if (auth == null ) {

			chain.doFilter(req, res);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(req, res);

	}

	

}
