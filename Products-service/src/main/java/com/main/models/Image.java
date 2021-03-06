package com.main.models;

import java.io.Serializable;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

@Entity
@Table(name = "images")
@Transactional
public class Image implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6616722223742012521L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id",nullable = false,unique = true)
	private String id;
	
	@Column(name = "name",nullable = false,unique = true)
	@Range(min = 2,max = 50,message = "Minimum characters 2, maximum characters 50!")
	private String name;
	
	@Column(name = "imageBytes")
	private Blob imageBytes;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", referencedColumnName = "id")
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

	public Blob getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(Blob imageBytes) {
		this.imageBytes = imageBytes;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
}

