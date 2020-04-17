package com.main.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.main.models.Category;
import com.main.models.Image;
import com.main.models.Order;
import com.main.models.Sale;

public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 5428167838808451593L;
	
	private String id;
	
	private String name;
	
	private Date dateCreated;
	
	//@JsonIgnore
	//private Category category;
	private String categoryId;
	
	//@JsonIgnore
	//private List<Image> images = new ArrayList<Image>();
	
	//private List<Order> orders = new ArrayList<Order>();
	
	//private List<Sale> sales = new ArrayList<Sale>();
	
	private Number price;
	
	private String description;
	
	private String material;

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
/*
	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
*/
	public Number getPrice() {
		return price;
	}

	public void setPrice(Number price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String category) {
		this.categoryId = category;
	}

}
