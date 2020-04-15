package com.main.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.main.services.CustomersService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	@Autowired
	private UserDetailsService detailsService;
	@Autowired
	private CustomersService customersService;
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
        http.csrf().disable();
		
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers("/**").hasIpAddress(this.env.getProperty("gateway.ip"))
		.and().addFilter(this.authenticationFilter());
		http.headers().frameOptions().disable();
		
	}
	
	protected AuthenticationFilter authenticationFilter() throws Exception {
		
		AuthenticationFilter filter = new AuthenticationFilter(env,
				                                               detailsService, 
				                                               this.authenticationManagerBean(),
				                                               customersService);
		
		filter.setFilterProcessesUrl(this.env.getProperty("customers.login.url.path"));
		
		return filter;
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) {
		
		auth.authenticationProvider(this.provider());
		
	}
	
	@Bean
	public DaoAuthenticationProvider provider() {
		
		DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
		
		prov.setPasswordEncoder(this.encoder());
		
		prov.setUserDetailsService(this.detailsService);
		
		return prov;
		
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	

}
