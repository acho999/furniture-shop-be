package com.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
import javax.transaction.Transactional;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "orders")
@Transactional
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
	private Boolean isPlaced;
	
	@Column(name = "payedOrders")
	private Boolean isPayed;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "Products_Orders",
	joinColumns = { @JoinColumn(name = "order_id",referencedColumnName = "id") }, 
	inverseJoinColumns = { @JoinColumn(name = "product_id",referencedColumnName = "id") })
	private List<Product> orderedProducts = new ArrayList<Product>();
	
	@Column(name = "sum")
	private Double sumOfOrder;
	
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

	public Boolean getIsPlaced() {
		return isPlaced;
	}

	public void setIsPlaced(Boolean isPlaced) {
		this.isPlaced = isPlaced;
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

	public Double getSumOfOrder() {
		return sumOfOrder;
	}

	public void setSumOfOrder(Double sumOfOrder) {
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
