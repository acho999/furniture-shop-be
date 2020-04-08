package com.main.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.models.Product;

public class CategoryDTO implements Serializable{
	
	
	private static final long serialVersionUID = -5244884936658916580L;


	private String id;
	
	
	private String name;
	
	@JsonIgnore
	private List<Product> products = new ArrayList<>();


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	} 


}
