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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@Entity
@Table(name = "products")
public class Product implements Serializable{
	
	
	private static final long serialVersionUID = 1123019031040034555L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id")
	private String id;
	
	@Column(name = "productName")
	private String name;
	
	@Column(name = "quantity")
	private Integer quantity;
	
	
	//@JsonIgnore
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_Id",referencedColumnName = "id")
	private Category category;
	
	//@JsonIgnore
	@OneToMany(mappedBy = "product",
			   targetEntity = Image.class,
			   fetch = FetchType.LAZY,
			   cascade = CascadeType.ALL,
			   orphanRemoval = true)
	private List<Image> images = new ArrayList<Image>();
	

	@ManyToMany(mappedBy = "orderedProducts",targetEntity = Order.class)
	private List<Order> orders = new ArrayList<Order>();
	
	@ManyToMany(mappedBy = "purchasedProducts", targetEntity = Sale.class)
	private List<Sale> sales = new ArrayList<Sale>();
	
	@Column(name = "price")
	private Double price;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "material")
	private String material;
	
	@Column(name = "dateCreated")
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

	public List<Order> getOrder() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Sale> getSale() {
		return sales;
	}

	public void setSale(List<Sale> sales) {
		this.sales = sales;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

