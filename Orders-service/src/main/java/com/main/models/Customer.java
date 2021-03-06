package com.main.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "customers")
@Transactional
public class Customer {

	@Id
	@GeneratedValue // (strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private String id;

	@Column(name = "firstName", nullable = false)
	private String first_name;

	@Column(name = "lastName", nullable = false)
	private String last_name;

	@Column(name = "userName", nullable = false, unique = true)
	@Range(min = 5,max = 25,message = "Minimum characters 5, maximum characters 25!")
	public String userName;

	@Column(name = "encryptedPassword", nullable = false)
	private String encryptedPassword;

	@Column(name = "email", nullable = false, unique = true)
	@Range(min = 5,max = 50,message = "Minimum characters 5, maximum characters 50!")
	@Email
	private String email;

	@Column(name = "date_created", nullable = false)
	private Date date_created;

	@OneToMany(mappedBy = "customer", targetEntity = Order.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Order> orders = new ArrayList<Order>();

	@OneToMany(mappedBy = "customer", targetEntity = Sale.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Sale> sales = new ArrayList<Sale>();

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

	public String getUsername() {
		return this.userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

}
