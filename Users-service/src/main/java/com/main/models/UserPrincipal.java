package com.main.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPrincipal implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String first_name;
	private String last_name;
	private String username;
	private String password;
	private String mail;
	private Admin user;
	private List<GrantedAuthority> authorities;
	
	public UserPrincipal(Admin userEntity) {
		
		this.user = userEntity;
		this.id = this.user.getId();
		this.first_name = this.user.getFirst_name();
		this.last_name = this.user.getLast_name();
		this.username = this.user.getUsername();
		this.password = this.user.getEncryptedPassword();
		this.mail = this.user.getEmail();
		this.authorities = new ArrayList<GrantedAuthority>();
		this.init(userEntity);
	}
	
	public void init(Admin userEntity) {
		GrantedAuthority role = new SimpleGrantedAuthority(userEntity.getRole().getAuthority());
		this.authorities.add(role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	
	@Override
	public String getUsername() {
		return this.username;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getId() {
		return this.id;
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

	public String getEmail() {
		return mail;
	}

	public void setEmail(String mail) {
		this.mail = mail;
	}

	public Admin getUser() {
		return user;
	}

	public void setUser(Admin user) {
		this.user = user;
	}


	public void setAuthorities(List<GrantedAuthority> roles) {
		this.authorities = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
