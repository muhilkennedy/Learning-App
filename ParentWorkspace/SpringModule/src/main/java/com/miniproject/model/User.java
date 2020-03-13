package com.miniproject.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.springframework.util.CollectionUtils;

import com.miniproject.util.CommonUtil;
import com.miniproject.util.LogUtil;

/**
 * @author Muhil Kennedy 
 * maps to user table which contains user information.
 */
@Entity
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USERID")
	private Integer userId;

	@Column(name = "EMAILID")
	private String emailId;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "ROLE")
	private String role;

	@Column(name = "FNAME")
	private String firstName;

	@Column(name = "LNAME")
	private String lastName;

	@Column(name = "ACTIVE")
	private boolean active;

	@Column(name = "LOGINVIA")
	private String loginVia;

	@OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
	private List<Address> address;
	
	@OneToOne(mappedBy = "userId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Verification verification;

	public User() {
	}

	public User(String emailId, String password, String mobile, String role, String firstName, String lastName,
			boolean active, String loginVia) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.mobile = mobile;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.active = active;
		this.loginVia = loginVia;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getLoginVia() {
		return loginVia;
	}

	public void setLoginVia(String loginVia) {
		this.loginVia = loginVia;
	}

	public List<Address> getAddress() {
		return address;
	}

	public void setAddress(List<Address> address) {
		this.address = address;
	}

	public Verification getVerification() {
		return verification;
	}

	public void setVerification(Verification verification) {
		this.verification = verification;
	}

	/**
	 * Perform default actions before final persist.
	 */
	@PrePersist
	private void prePersistUser() {
		LogUtil.getLogger().info("Pre-Persit User Object : " + this.getUserId());
		if (CommonUtil.isNullOrEmptyString(this.loginVia)) {
			this.loginVia = CommonUtil.internalUser;
		}
		if (CommonUtil.isNullOrEmptyString(this.role)) {
			this.role = CommonUtil.userPermission;
		}
		if (!CollectionUtils.isEmpty(this.address)) {
			this.address.stream().forEach(item -> {
				item.setUserId(this);
			});
		}
	}

}
