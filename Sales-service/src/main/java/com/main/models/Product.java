package com.main.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product implements Serializable {

	private static final long serialVersionUID = 1123019031040034555L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id",unique = true,nullable = false)
	private String id;

	@Column(name = "productName",nullable = false)
	private String name;

	// @JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_Id", referencedColumnName = "id")
	private Category category;

	@OneToMany(mappedBy = "product", targetEntity = Image.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Image> images = new ArrayList<Image>();
	
	@OneToMany(mappedBy = "product", targetEntity = OrderedProduct.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<OrderedProduct> orders = new ArrayList<OrderedProduct>();
	
	@OneToMany(mappedBy = "product", targetEntity = SoldProduct.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<SoldProduct> sales = new ArrayList<SoldProduct>();
	
	@Column(name = "price",nullable = false)
	private Double price;

	@Column(name = "description",nullable = false)
	private String description;

	@Column(name = "material",nullable = false)
	private String material;

	@Column(name = "dateCreated",nullable = false)
	private Date dateCreated;

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
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

}
