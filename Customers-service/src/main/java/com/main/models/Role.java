package com.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="roles")
public class Role implements GrantedAuthority,Serializable{
	
	private static final long serialVersionUID = -5273232412342674557L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private long id;

	@Column(name = "roleName", nullable = false)
	@Range(min  = 2,max = 50, message = "Minimum characters 2,maximum characters 50!")
	private String roleName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "role",
			   targetEntity = User.class,
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL)
	public List<User> admins = new ArrayList<>();
	
	
	@Override
	public String getAuthority() {
		return this.roleName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getAdmins() {
		return this.admins;
	}

	public void setAdmins(List<User> admins) {
		this.admins = admins;
	}

}
