package com.miniproject.model;

import java.math.BigDecimal;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Mohan
 * Granular items
 */
@Entity
@Table(name = "ITEM")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEMID")
	private Integer itemId;
	
	@Column(name = "ITEM")
	private String itemName;
	
	@Column(name = "BRANDNAME")
	private String brandName;
	
	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name = "MEASURE")
	private String measure;
	
	@Column(name = "OFFER")
	private Integer offer;
	
	@Column(name = "IMAGE")
	private Blob image;
	
	@Column(name = "ACTIVE")
	private boolean active;

	@ManyToOne
	@JoinColumn(name = "CID", nullable = false)
	private Category cId;

	public Item() {}
	
	public Item(String itemName, String brandName, BigDecimal cost, String measure, Integer offer, Blob image,
			Category cId) {
		this.itemName = itemName;
		this.brandName = brandName;
		this.cost = cost;
		this.measure = measure;
		this.offer = offer;
		this.image = image;
		this.cId = cId;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	public String getMeasure() {
		return measure;
	}

	public void setMeasure(String measure) {
		this.measure = measure;
	}

	public Integer getOffer() {
		return offer;
	}

	public void setOffer(Integer offer) {
		this.offer = offer;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Category getcId() {
		return cId;
	}

	public void setcId(Category cId) {
		this.cId = cId;
	}

}
