package com.miniproject.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Mohan
 * maps to cart user and item as composite.
 */
@Entity
@Table(name = "CART")
public class Cart implements Serializable{

	@EmbeddedId
	@ManyToOne
	@JoinColumn(name = "USERID", nullable = false)
	private User userId;
	
	@Column(name = "ITEMID")
	private Integer itemId;
	
	@Column(name = "QUANTITY")
	private Integer quantity;

	public Cart() {}
	
	public Cart(User userId, Integer itemId, Integer quantity) {
		this.userId = userId;
		this.itemId = itemId;
		this.quantity = quantity;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
