package com.main.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "SoldProducts")
public class SoldProduct implements Serializable{
	
	private static final long serialVersionUID = -8827420524947491977L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private String id;

	@Column(name = "dateCreated")
	private Date dateCreated;
	
	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name = "sale_id",referencedColumnName = "id")
	private Sale sale;
	
	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private Product product;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}
