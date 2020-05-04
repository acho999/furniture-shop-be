package com.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
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
	
	private Environment env;
	private String ip;
	
	@Autowired
	public SecurityConfig(Environment env) {
		
		this.env = env;
		this.ip = env.getProperty("gateway.ip");
	}
	
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers("/customers/create").permitAll()
		.antMatchers("/customers/**").hasIpAddress(ip)
		.antMatchers("/customers/details/{id}").hasAnyRole("ADMIN","CUSTOMER")
		.antMatchers("/customers/getAll").hasAnyRole("ADMIN")
		.antMatchers("/customers/update").hasRole("CUSTOMER")
		.antMatchers("/customers/delete/{id}").hasAnyRole("ADMIN","CUSTOMER")
		.anyRequest().authenticated()
		.and().addFilterBefore(new AuthorizaionFilter(authenticationManagerBean()), 
				BasicAuthenticationFilter.class);
		//.and().addFilter(new AuthorizaionFilter(authenticationManagerBean(),this.environment,this.service));
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
}

