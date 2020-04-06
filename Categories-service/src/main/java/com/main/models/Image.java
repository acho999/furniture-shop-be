package com.main.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image {
	
	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "id")
	private String name;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "productId", referencedColumnName = "id")
	private Product product;
	
}
