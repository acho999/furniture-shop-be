package com.main.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.main.models.Category;
import com.main.models.Image;

public class ProductDTO implements Serializable{

	private static final long serialVersionUID = 5428167838808451593L;
	
	
	private String id;
	
	
	private String name;
	
	@JsonIgnore
	private Category category;
	
	@JsonIgnore
	private List<Image> images = new ArrayList<Image>();
	
	
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
	

}
