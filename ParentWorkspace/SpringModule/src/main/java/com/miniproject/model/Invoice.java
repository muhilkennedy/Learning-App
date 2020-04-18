package com.miniproject.model;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Mohaneswaran
 *
 *         maps with order table with many to one cardinality.
 */
@Entity
@Table(name = "INVOICE")

public class Invoice {
	
	@OneToOne
	@JoinColumn(name = "ORDERID", nullable = false)
	private Orders orderId;
	
	@Column(name = "DOCUMENT")
	private Blob document;

	public Invoice(Orders orderId, Blob document) {
		super();
		this.orderId = orderId;
		this.document = document;
	}

	public Orders getOrderId() {
		return orderId;
	}

	public void setOrderId(Orders orderId) {
		this.orderId = orderId;
	}

	public Blob getDocument() {
		return document;
	}

	public void setDocument(Blob document) {
		this.document = document;
	}
	
	


}
