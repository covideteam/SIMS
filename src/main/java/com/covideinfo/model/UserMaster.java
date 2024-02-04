package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "epk_user_master")
public class UserMaster implements Serializable{
	
	
	
	
	private static final long serialVersionUID = 1311640535272891776L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "epk_user_master_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "userId")
	private long id;
	@Column(name = "USER_NAME", unique = true, nullable = false)
	private String username;
	@Column(name = "PASSWORD", nullable = false)
	private String password;	
	@Column(name = "FULL_NAME", nullable = false)
	private String fullName;
	@ManyToOne(cascade=CascadeType.REFRESH)
	@JoinColumn
	private RoleMaster role;
	@Column(name="ACCOUNT_NOT_LOCK")
	private boolean accountNotLock = true;
	@Column(name="ACCOUNT_NOT_DISABLE")
	private boolean accountNotDisable = true;
	@Column(name="ACCOUNT_EXPIRE_DATE")
	private Date accountexprie;
	private String tranPassword;
	@Column(name="email")
	private String email;
	@ManyToOne
	@JoinColumn(name="createdby")
	private UserMaster createdBy;
	@Column(name="createdon")
	private Date createdOn;
	@ManyToOne
	@JoinColumn(name="updatedby")
	private UserMaster updatedBy;
	@Column(name="updatedon")
	private Date updatedOn;
	@Column(name="update_reason")
	private String updateReason;
	
	/*@Column(name="confirmPassword")
	private String confirmPassword;*/
	
	@Column(columnDefinition="BOOLEAN DEFAULT false")
	private boolean pwdChangeCundition;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public RoleMaster getRole() {
		return role;
	}
	public void setRole(RoleMaster role) {
		this.role = role;
	}
	public boolean isAccountNotLock() {
		return accountNotLock;
	}
	public void setAccountNotLock(boolean accountNotLock) {
		this.accountNotLock = accountNotLock;
	}
	public boolean isAccountNotDisable() {
		return accountNotDisable;
	}
	public void setAccountNotDisable(boolean accountNotDisable) {
		this.accountNotDisable = accountNotDisable;
	}
	public Date getAccountexprie() {
		return accountexprie;
	}
	public void setAccountexprie(Date accountexprie) {
		this.accountexprie = accountexprie;
	}
	public String getTranPassword() {
		return tranPassword;
	}
	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public UserMaster getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(UserMaster updatedBy) {
		this.updatedBy = updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	/*public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	*/
	public boolean isPwdChangeCundition() {
		return pwdChangeCundition;
	}
	public void setPwdChangeCundition(boolean pwdChangeCundition) {
		this.pwdChangeCundition = pwdChangeCundition;
	}
	
	
	
}
