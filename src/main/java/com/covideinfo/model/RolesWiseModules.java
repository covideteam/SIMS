package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="role_wise_modules")
public class RolesWiseModules implements Serializable{
	
	
	private static final long serialVersionUID = -326057109627430635L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="role_wise_module_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="user_main")
	private ApplicationMenus usermenu;
	@ManyToOne
	@JoinColumn(name="app_side_menu")
	private ApplictionSideMenus appsideMenu;
	@ManyToOne
	@JoinColumn(name="role_id")
	private RoleMaster role;
	@Column(name="status")
	private String status= "active";
	@Column(name="addStatus")
	private String addStatus= "inactive";
	@Column(name="update")
	private String update= "inactive";
	@Column(name="review")
	private String review= "inactive";
	@Column(name="deactive")
	private String deactive= "inactive";
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ApplicationMenus getUsermenu() {
		return usermenu;
	}
	public void setUsermenu(ApplicationMenus usermenu) {
		this.usermenu = usermenu;
	}
	public ApplictionSideMenus getAppsideMenu() {
		return appsideMenu;
	}
	public void setAppsideMenu(ApplictionSideMenus appsideMenu) {
		this.appsideMenu = appsideMenu;
	}
	public RoleMaster getRole() {
		return role;
	}
	public void setRole(RoleMaster role) {
		this.role = role;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public String getAddStatus() {
		return addStatus;
	}
	public void setAddStatus(String addStatus) {
		this.addStatus = addStatus;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public String getDeactive() {
		return deactive;
	}
	public void setDeactive(String deactive) {
		this.deactive = deactive;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
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
	
	
	
	

}
