package com.miniproject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Mohaneswaran
 *
 *         maps with user table with many to one cardinality.
 */
@Entity
@Table(name = "ORDERS")

public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDERID")
	private Integer orderId;
	
	@ManyToOne
	@JoinColumn(name = "USERID", nullable = false)
	private User userId;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "ORDERDATE")
	private Date orderDate;
	
	public Orders( User userId, String status, Date orderDate) {
		this.userId = userId;
		this.status = status;
		this.orderDate = orderDate;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	
	



}
