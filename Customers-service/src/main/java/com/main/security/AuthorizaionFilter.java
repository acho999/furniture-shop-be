package com.main.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class AuthorizaionFilter extends BasicAuthenticationFilter {

	public AuthorizaionFilter(AuthenticationManager manager) {
		super(manager);

	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		System.out.println(req);

		System.out.println(
				req.getHeader("X-Zuul-UserId") + " " + req.getHeader("X-Zuul-UserRoles") + req.getHeader("client"));

		String id = "";

		String[] rolesArr = null;

		String clientString = null;

		if (req.getHeader("X-Zuul-UserId") != null && req.getHeader("X-Zuul-UserRoles") != null
				&& req.getHeader("client") == null) {

			id = req.getHeader("X-Zuul-UserId");

			rolesArr = req.getHeader("X-Zuul-UserRoles").split(" ");

		}

		if (id == null && rolesArr == null && clientString == null) {// || !authHeader.startsWith(this.headerPrefix)) {

			chain.doFilter(req, res);
			return;
		}

		List<GrantedAuthority> roles = new ArrayList<>();

		if (req.getHeader("client") != null && req.getHeader("client").equals("users")) {

			id = "user";
			roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

		} else {
			
			for (int i = 0; i < rolesArr.length; i++) {

				roles.add(new SimpleGrantedAuthority(rolesArr[i].toUpperCase()));// "ROLE_" +
																					// (roles1[i].toUpperCase())));
				//
			}

		}

		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(id, null, roles);

		if (auth == null) {

			chain.doFilter(req, res);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(req, res);

	}

}
