package com.miniproject.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;

/**
 * @author MuhilKennedy
 *
 */
@Entity
@Table(name = "VERIFICATION")
public class Verification {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer Id;
	
	@OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USERID", nullable = false)
    private User userId;
	
	@Column(name="CODE")
	private String code;
	
	@UpdateTimestamp
	@Column(name="TIMECREATED")
	private Date timeCreated;

	public Verification() {}
	
	public Verification(User userId, String code, Date timeCreated) {
		this.userId = userId;
		this.code = code;
		this.timeCreated = timeCreated;
	}

	public Integer getId() {
		return Id;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	
}
