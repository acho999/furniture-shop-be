package com.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private Environment environment;
	
	@Autowired
	public SecurityConfig(Environment environment) {
		this.environment = environment;
	}
	
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.headers().frameOptions().disable();
		//.access("hasRole('ROLE_CUSTOMER')")
		http.authorizeRequests()
		.antMatchers(HttpMethod.POST,this.environment.getProperty("api.registration.url.path")).permitAll()
		.antMatchers(HttpMethod.POST,this.environment.getProperty("api.login.url.path")).permitAll()
		.antMatchers("/**").hasRole("CUSTOMER").anyRequest().authenticated()
		.and().addFilter(new AuthorizaionFilter(authenticationManagerBean(),this.environment));

		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
	}
	
	
	
}
