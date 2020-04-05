package com.main.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginRequestModel {
	
	@NotNull
	@Size(min=6,max=20)
	private String userName;
	@NotNull
	@Size(min=6,max=20)
	private String password;
	
	public String getUsername() {
		return userName;
	}
	public void setUsername(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
