package com.main.models;

import java.io.Serializable;
import java.util.Date;
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
	
	@Column(name = "dateCreated")
	private Date dateCreated;
	
	@ManyToMany(mappedBy = "sales",targetEntity = Product.class)
	private List<Product> purchasedProducts = new ArrayList<Product>();
	
	@Column(name = "sum")
	private Number sumOfSale;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date date) {
		this.dateCreated = date;
	}

	public List<Product> getPurchasedProducts() {
		return purchasedProducts;
	}

	public void setPurchasedProducts(List<Product> purchasedProducts) {
		this.purchasedProducts = purchasedProducts;
	}

	public Number getSumOfSale() {
		return sumOfSale;
	}

	public void setSumOfSale(Number sumOfSale) {
		this.sumOfSale = sumOfSale;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}

