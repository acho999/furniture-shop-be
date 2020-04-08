package com.main.DTO;

import com.main.models.Product;

import oracle.sql.BlobDBAccess;

public class ImageDTO {
	
	private String id;
	
	private String name;
	
	private BlobDBAccess imageBytes;

	private Product product;

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

	public BlobDBAccess getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(BlobDBAccess imageBytes) {
		this.imageBytes = imageBytes;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	

}
