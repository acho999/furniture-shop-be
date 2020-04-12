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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.core.sym.Name;

@Entity
@Table(name = "products")
public class Product implements Serializable{
	
	
	private static final long serialVersionUID = 1123019031040034555L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id")
	private String id;
	
	@Column(name = "productName")
	private String name;
	
	//@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_Id",referencedColumnName = "id")
	private Category category;
	
	//@JsonIgnore
	@OneToMany(mappedBy = "product",
			   targetEntity = Image.class,
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	private List<Image> images = new ArrayList<Image>();
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Products_Orders", 
        joinColumns = { @JoinColumn(name = "order_id",referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "product_id",referencedColumnName = "id") }
    )
	private List<Order> orders = new ArrayList<Order>();
	
	@ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
        name = "Products_Sales", 
        joinColumns = { @JoinColumn(name = "sale_id",referencedColumnName = "id") }, 
        inverseJoinColumns = { @JoinColumn(name = "product_id",referencedColumnName = "id") }
    )
	private List<Sale> sales = new ArrayList<Sale>();
	
	@Column(name = "price")
	private Number price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "material")
	private String material;
	
}

