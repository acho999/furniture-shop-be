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
@Table(name = "OrderedProducts")
public class OrderedProduct implements Serializable{
	
	
	private static final long serialVersionUID = -8647951915942986729L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "id")
	private String id;

	@Column(name = "dateCreated")
	private Date dateCreated;
	
	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name = "order_id",referencedColumnName = "id")
	private Order order;

	@ManyToOne(optional = false,fetch = FetchType.EAGER)
	@JoinColumn(name = "product_id",referencedColumnName = "id")
	private Product product;
		
		

}
