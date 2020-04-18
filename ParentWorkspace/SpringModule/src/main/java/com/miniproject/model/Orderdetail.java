package com.miniproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Mohaneswaran
 *
 *         maps with order and item table with many to one cardinality.
 */
@Entity
@Table(name = "ORDERDETAIL")

public class Orderdetail {
	
	@ManyToMany
	@JoinColumn(name = "ORDERID", nullable = false)
	private Orders orderId;
	
	@ManyToMany
	@JoinColumn(name = "ITEMID", nullable = false)
	private Item itemId;
	
	@Column(name = "QUANTITY")
	private Integer quantity;

	public Orderdetail(Orders orderId, Item itemId, Integer quantity) {
		super();
		this.orderId = orderId;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public Orders getOrderId() {
		return orderId;
	}

	public void setOrderId(Orders orderId) {
		this.orderId = orderId;
	}

	public Item getItemId() {
		return itemId;
	}

	public void setItemId(Item itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	

}
