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
		.antMatchers("/sales/**").hasIpAddress(this.env.getProperty("gateway.ip"))
		.antMatchers("/sales/hello").hasAnyRole("ADMIN")
		.antMatchers("/sales/details/{id}").hasAnyRole("ADMIN","CUSTOMER")
		.antMatchers("/sales/create").hasAnyRole("ADMIN","CUSTOMER")
		.antMatchers("/sales/getAll").hasAnyRole("ADMIN")
		.antMatchers("/orders/getAllCustomerSales{customerId}").hasAnyRole("ADMIN","CUSTOMER")
		.antMatchers("/sales/update").hasRole("ADMIN")
		.antMatchers("/sales/delete/{id}").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().addFilterBefore(new AuthorizaionFilter(authenticationManagerBean()), 
				BasicAuthenticationFilter.class);
		//.and().addFilter(new AuthorizaionFilter(authenticationManagerBean(),this.environment,this.service));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
}

