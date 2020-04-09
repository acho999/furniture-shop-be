package com.main.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerPrincipal implements UserDetails{
	
	
	private static final long serialVersionUID = 4910574251663258731L;

	private Customer customer;
	
	private List<GrantedAuthority> authorities;
	
	public CustomerPrincipal(Customer customer) {
		
		this.customer = this.customer;
		
		this.authorities = new ArrayList<GrantedAuthority>();
		
		this.init(customer);
	}
	
	private void init(Customer customer) {
		
		SimpleGrantedAuthority role = new SimpleGrantedAuthority(customer.getRole().getAuthority());
		
		this.authorities.add(role);
		
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.authorities;
	}

	@Override
	public String getPassword() {

		return this.customer.getEncryptedPassword();
	}

	@Override
	public String getUsername() {

		return this.customer.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {

		return false;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}

	@Override
	public boolean isEnabled() {

		return true;
	}

}
