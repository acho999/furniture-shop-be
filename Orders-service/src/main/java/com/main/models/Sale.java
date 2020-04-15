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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "sales")
public class Sale implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7783074861833597371L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id")
	private String id;
	
	@ManyToMany(mappedBy = "sales",targetEntity = Product.class)
	private List<Product> purchasedProducts = new ArrayList<Product>();
	
	@Column(name = "sum")
	private Number sumOfSale;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

}
