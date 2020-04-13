package com.main.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.main.models.Order;
import com.main.models.Role;
import com.main.models.Sale;

public class CustomerDTO implements Serializable{
	
	private static final long serialVersionUID = 4493033739701091347L;

	private String id;
	
	private String first_name;
	
	private String last_name;
	
	public String username;
	
	private String password;
	
	private String encryptedPassword;
	
	private String email;
	
	private Date date_created;
	
	//@JsonIgnore
	public Role role;
	
	//@JsonIgnore
	private List<Order> orders = new ArrayList<Order>();
	
	//@JsonIgnore
	private List<Sale> sales = new ArrayList<Sale>();
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setFirst_name(String firstName) {
		this.first_name = firstName;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String lastName) {
		this.last_name = lastName;
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

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}


}
