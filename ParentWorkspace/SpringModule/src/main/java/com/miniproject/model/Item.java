package com.miniproject.model;

import java.math.BigDecimal;
import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Mohaneswaran
 *
 *         maps with Category table with many to one cardinality.
 */

@Entity
@Table(name = "ITEM")
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ITEMID")
	private Integer itemId;
	
	@ManyToMany
	@JoinColumn(name = "CID", nullable = false)
	private Category cId;
	
	@Column(name = "ITEM")
	private String item;
	
	@Column(name = "BRANDNAME")
	private String brandName;
	
	@Column(name = "COST")
	private BigDecimal cost;
	
	@Column(name = "MEASURE")
	private String measure;
	
	@Column(name = "OFFER")
	private BigDecimal offer;
	
	@Column(name = "IMAGE")
	private Blob image;
	
	@Column(name = "ACTIVE")
	private boolean active;
	
	public Item( Category cId, String item, String brandName, BigDecimal cost, String measure,
			BigDecimal offer, Blob image) {
		this.cId = cId;
		this.item = item;
		this.brandName = brandName;
		this.cost = cost;
		this.measure = measure;
		this.offer = offer;
		this.image = image;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Category getcId() {
		return cId;
	}

	public void setcId(Category cId) {
		this.cId = cId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
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

	public BigDecimal getOffer() {
		return offer;
	}

	public void setOffer(BigDecimal offer) {
		this.offer = offer;
	}

	public Blob getImage() {
		return image;
	}

	public void setImage(Blob image) {
		this.image = image;
	}

}
