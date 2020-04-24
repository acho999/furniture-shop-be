package com.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	/*private Environment environment;
	private UserDetailsService service;
	
	@Autowired
	public SecurityConfig(Environment environment, UserDetailsService service) {
		this.environment = environment;
		this.service = service;
	}*/
	
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable();
		
		http.headers().frameOptions().disable();
		http.authorizeRequests()
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
	
	/*@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(this.service);
		
		return provider;
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}*/
	
}

