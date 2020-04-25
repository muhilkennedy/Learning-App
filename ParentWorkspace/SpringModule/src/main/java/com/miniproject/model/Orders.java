package com.miniproject.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;

/**
 * @author Mohan
 * has many to one cardinality with users.
 *
 */
@Entity
@Table(name = "ORDERS")
public class Orders {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORDERID")
	private Integer orderId;
	
	@Column(name = "STATUS")
	private String status;
	
	@UpdateTimestamp
	@Column(name="ORDERDATE")
	private Date orderDate;
	
	@ManyToOne
	@JoinColumn(name = "USERID", nullable = false)
	private User userId;
	
	@OneToMany(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<OrderDetail> orderDetail;
	
	@OneToOne(mappedBy = "orderId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Invoice invoice;
	
	public Orders() {}
	
	public Orders(String status, Date orderDate, User userId) {
		this.status = status;
		this.orderDate = orderDate;
		this.userId = userId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
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

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	/**
	 * Perform default actions before final persist.
	 */
	@PrePersist
	private void prePersistUser() {
		LogUtil.getLogger().info("Pre-Persit Order Object : " + this.orderId);
		if (CommonUtil.isNullOrEmptyString(this.status)) {
			this.status = CommonUtil.Key_PendingStatus;
		}
	}
	
}
