package com.main.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.main.services.UsersService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Environment env;
	private UsersService service;
	private String ip;
	private String loginPath;
	
	@Autowired
	public SecurityConfig(Environment env, @Lazy UsersService service) {
		
		this.env = env;
		this.service = service;
		this.loginPath = env.getProperty("users.login.url.path");
		this.ip = env.getProperty("gateway.ip");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
        http.csrf().disable();
		
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers("/**").hasIpAddress(ip)
		.and().addFilter(getAuthenticationFilter());
		http.headers().frameOptions().disable();
		
		
	}
	
	protected AuthenticationFilter getAuthenticationFilter() throws Exception {
		
		AuthenticationFilter filter = new AuthenticationFilter(this.env,this.service, authenticationManagerBean());
		
		filter.setFilterProcessesUrl(loginPath);
		
		return filter;
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authProvider());
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(encoder());
		provider.setUserDetailsService(this.service);
		
		return provider;
		
	}
	
	@Bean
	public BCryptPasswordEncoder encoder() {
		
		return new BCryptPasswordEncoder();
	}

}
