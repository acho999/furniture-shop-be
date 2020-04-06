package com.main.models;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product implements Serializable{
	
	
	private static final long serialVersionUID = 1123019031040034555L;

	@Id
	@GeneratedValue
	private String id;
	
	@Column(name = "productName")
	private String name;
	
	@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "productId",referencedColumnName = "id")
	private Category category;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product",
			   targetEntity = Image.class,
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	private List<Image> images = new ArrayList<Image>();
	
	@Column(name = "price")
	private Number price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "material")
	private String material;
	
}

