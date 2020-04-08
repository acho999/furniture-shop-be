package com.main.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "sales")
public class Sale {
	
	private String id;
	
	@ManyToMany(mappedBy = "sales",targetEntity = Product.class)
	private List<Product> purchasedProducts = new ArrayList<Product>();
	
	@Column(name = "sum")
	private Number sumOfSale;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

}
