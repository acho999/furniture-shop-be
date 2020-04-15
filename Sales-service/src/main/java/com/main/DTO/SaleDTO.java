package com.main.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.main.models.Customer;
import com.main.models.Product;

public class SaleDTO implements Serializable{
	
	
	private static final long serialVersionUID = 5121390665718527368L;

	
	private String id;
	
	private List<Product> purchasedProducts = new ArrayList<Product>();
	
	private Number sumOfSale;
	
	private Customer customer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
