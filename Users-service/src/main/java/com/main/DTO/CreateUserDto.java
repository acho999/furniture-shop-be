package com.main.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.lang.Nullable;

public class CreateUserDto {
	
	@NotNull
	@Size(min=2,max=20)
	private String username;
	
	@NotNull
	@Size(min=6,max=12)
	private String password;
	
	@NotNull
	@Size(min=2,max=50)
	private String first_name;
	
	@NotNull
	@Size(min=2,max=50)
	private String last_name;
	
	@NotNull
	@Size(min=6,max=50)
	private String email;
	
	@Nullable
	private String adminRegSecret;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdminRegSecret() {
		return adminRegSecret;
	}

	public void setAdminRegSecret(String adminRegSecret) {
		this.adminRegSecret = adminRegSecret;
	}
	

}
