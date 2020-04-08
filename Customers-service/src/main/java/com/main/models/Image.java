package com.main.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import oracle.sql.BlobDBAccess;

@Entity
@Table(name = "images")
public class Image {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "id")
	private String name;
	
	@Column(name = "imageBytes")
	private BlobDBAccess imageBytes;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", referencedColumnName = "id")
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

