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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "sales")
@Transactional
public class Sale implements Serializable{
	
	private static final long serialVersionUID = 7783074861833597371L;

	@Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
        name = "UUID",
    strategy = "org.hibernate.id.UUIDGenerator"
    )
	@Column(name = "id")
	private String id;
	
	@Column(name = "dateCreated")
	private Date dateCreated;
	
	@OneToMany(mappedBy = "sale",targetEntity = SoldProduct.class,fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REMOVE})
	private List<SoldProduct> sale = new ArrayList<SoldProduct>();
	
	@Column(name = "sum")
	private Double sumOfSale;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "customer_id", referencedColumnName = "id")
	private Customer customer;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date date) {
		this.dateCreated = date;
	}

	public Double getSumOfSale() {
		return sumOfSale;
	}

	public void setSumOfSale(Double sumOfSale) {
		this.sumOfSale = sumOfSale;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<SoldProduct> getSale() {
		return sale;
	}

	public void setSale(List<SoldProduct> sale) {
		this.sale = sale;
	}

}





