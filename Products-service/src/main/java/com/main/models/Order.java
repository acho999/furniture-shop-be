package com.main.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private String id;
	
	@Column(name = "placedOrders")
	private Boolean isPlased;
	
	@Column(name = "payedOrders")
	private Boolean isPayed;
	
	@ManyToMany(mappedBy = "orders",targetEntity = Product.class)
	private List<Product> orderedProducts = new ArrayList<Product>();
	
	@Column(name = "sum")
	private Number sumOfOrder;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id",referencedColumnName = "id" )
	private Customer customer;

}