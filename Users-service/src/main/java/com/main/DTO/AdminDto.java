package com.main.DTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

import com.main.models.Role;

import antlr.Token;

public class AdminDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String first_name;
	private String last_name;
	private String username;
	private String email;
	private Role role;
	private Date date_created;
	private String password;
	private String adminRegSecret;
	
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
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String mail) {
		this.email = mail;
	}
	public Role getRole() {
		return this.role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Date getDate_created() {
		return date_created;
	}
	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAdminRegSecret() {
		return adminRegSecret;
	}
	public void setAdminRegSecret(String adminRegSecret) {
		this.adminRegSecret = adminRegSecret;
	}
	

}
