package com.main.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.main.models.Customer;
import com.main.models.Product;

public class OrderDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5263771987777005539L;

	private String id;
	
	private Boolean isPlased;
	
	private Boolean isPayed;
	
	private List<Product> orderedProducts = new ArrayList<Product>();
	
	private Number sumOfOrder;
	
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

}
