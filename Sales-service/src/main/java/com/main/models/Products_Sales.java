package com.main.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Products_Sales")
public class Products_Sales {
	
	@Column(name="sale_id",nullable = false)
	private String sale_id;
	
	@Column(name="product_id",nullable = false)
	private String product_id;

}
