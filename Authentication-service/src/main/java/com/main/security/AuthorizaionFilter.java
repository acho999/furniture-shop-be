package com.main.security;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class AuthorizaionFilter extends BasicAuthenticationFilter {

	private String headerName;
	private String headerPrefix;
	private String tokenSecret;

	private Environment env;

	@Autowired
	public AuthorizaionFilter(AuthenticationManager manager, Environment env) {
		super(manager);
		this.env = env;
		this.tokenSecret = this.env.getProperty("token.secret");
		this.headerPrefix = this.env.getProperty("authorization.token.header.prefix");
		this.headerName = this.env.getProperty("authorization.token.header.name");

	}

	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		String authHeader = req.getHeader(this.headerName);

		if (authHeader == null || !authHeader.startsWith(this.headerPrefix)) {

			chain.doFilter(req, res);
			return;
		}

		UsernamePasswordAuthenticationToken auth = this.getAuthentication(req);

		if (auth == null ) {

			chain.doFilter(req, res);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(auth);
		chain.doFilter(req, res);

	}

	protected UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest req) {

		String token = "";
		Jws<Claims> body = null;

		try {

			String tokenWithPrefix = req.getHeader(this.headerName);

			if (tokenWithPrefix != null) {

				token = tokenWithPrefix.replace(this.headerPrefix, "");

				body = Jwts.parser().setSigningKey(this.tokenSecret).parseClaimsJws(token);
			}

			if (this.headerName == null || body == null || (body.getBody().getExpiration().before(new Date()))) {
				return null;
			} 
			
			ArrayList<LinkedHashMap<String, String>> map = (ArrayList<LinkedHashMap<String, String>>) body.getBody().get("roles");
			
			List<GrantedAuthority> roles = new ArrayList<>();
			
			for (int i = 0; i < map.size(); i++) {
				
				roles.add(new SimpleGrantedAuthority("ROLE_" + (map.get(i).get("authority")).toUpperCase()));
				// 
			}
			
			
			
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(body.getBody().getSubject(),null,roles);
			
			return authenticationToken;

		} catch (JwtException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
			throw new JwtAuthenticationException("JWT is expired or invalid!");

		}

	}

}
