package com.main.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomerPrincipal implements UserDetails{
	
	
	private static final long serialVersionUID = 4910574251663258731L;
	
	private String id;
	private String first_name;
	private String last_name;
	private String username;
	private String encryptedPassword;
	private String email;

	private Customer customer;
	
	private List<GrantedAuthority> authorities;
	
	public CustomerPrincipal(Customer customer) {
		
		this.id = customer.getId();
		this.first_name = customer.getFirst_name();
		this.last_name = customer.getLast_name();
		this.username = customer.getUsername();
		this.encryptedPassword = customer.getEncryptedPassword();
		this.email = customer.getEmail();
		
		this.customer = customer;
		
		this.authorities = new ArrayList<GrantedAuthority>();
		
		this.init(customer);
	}
	
	private void init(Customer customer) {
		
		GrantedAuthority role = new SimpleGrantedAuthority(customer.getRole().getAuthority());
		
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

		return true;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setAuthorities(List<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

}
