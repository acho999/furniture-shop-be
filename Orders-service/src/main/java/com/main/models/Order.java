package com.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
	
	@Column(name = "dateCreated")
	private Date dateCreated;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id",referencedColumnName = "id" )
	private Customer customer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsPlased() {
		return isPlased;
	}

	public void setIsPlased(Boolean isPlased) {
		this.isPlased = isPlased;
	}

	public Boolean getIsPayed() {
		return isPayed;
	}

	public void setIsPayed(Boolean isPayed) {
		this.isPayed = isPayed;
	}

	public List<Product> getOrderedProducts() {
		return orderedProducts;
	}

	public void setOrderedProducts(List<Product> orderedProducts) {
		this.orderedProducts = orderedProducts;
	}

	public Number getSumOfOrder() {
		return sumOfOrder;
	}

	public void setSumOfOrder(Number sumOfOrder) {
		this.sumOfOrder = sumOfOrder;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

}
