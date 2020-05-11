package com.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.headers().frameOptions().disable();
		http.authorizeRequests()
		.antMatchers("/categories/**").hasIpAddress(this.env.getProperty("gateway.ip"))
		.antMatchers("/categories/hello").hasAnyRole("ADMIN")
		.antMatchers("/categories/details/{id}").hasAnyRole("ADMIN")
		.antMatchers("/categories/create").hasAnyRole("ADMIN")
		.antMatchers("/categories/getAll").hasAnyRole("ADMIN")
		.antMatchers("/categories/update").hasRole("ADMIN")
		.antMatchers("/categories/delete/{id}").hasAnyRole("ADMIN")
		.antMatchers("/categories").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and().addFilterBefore(new AuthorizaionFilter(authenticationManagerBean()), 
				BasicAuthenticationFilter.class);
		//.and().addFilter(new AuthorizaionFilter(authenticationManagerBean(),this.environment,this.service));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	
}

