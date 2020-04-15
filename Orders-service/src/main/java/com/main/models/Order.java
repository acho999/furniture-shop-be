package com.main.models;

import java.io.Serializable;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "orders")
public class Order implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2025019082654076408L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
    )
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
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id",referencedColumnName = "id" )
	private Customer customer;

}
