package com.miniproject.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Mohaneswaran
 *
 *         Maps with user and item table with many to one cardinality.
 */

@Entity
@Table(name = "CART")

public class Cart {
	
	@OneToOne
	@JoinColumn(name = "USERID", nullable = false)
	private User userId;
	
	@OneToMany
	@JoinColumn(name = "ITEMID", nullable = false)
	private Item itemId;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	

	public Cart(User userId, Item itemId, Integer quantity) {
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
