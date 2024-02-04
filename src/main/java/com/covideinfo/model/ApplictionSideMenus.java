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
import javax.persistence.Transient;

/*@SuppressWarnings("serial")*/
@Entity(name="app_side_menus")
public class ApplictionSideMenus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8010808382971380143L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="app_side_menus_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name="app_menu")
	private ApplicationMenus appMenu;
	@Column(name="controller_name")
	private String controllerName;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Column(columnDefinition="BOOLEAN DEFAULT true")
	public boolean linkStatus;
	
	@Transient
	private String addStatus;
	@Transient
	private String update;
	@Transient
	private String review;
	@Transient
	private String deactive;
	
	@Transient
	private String statusDeactive;
	
	@Transient
	private String status;
	
	@Transient
	private String statusAdd;
	@Transient
	private String statusUpdate;
	@Transient
	private String statusReview;
	
	public ApplictionSideMenus() {
		
	}
	
	
	public ApplictionSideMenus(String name, ApplicationMenus appMenu, String createdBy, Date createdOn, String controllerName,boolean linkStatus) {
		super();
		this.name = name;
		this.appMenu = appMenu;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.controllerName = controllerName;
		this.linkStatus=linkStatus;
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public ApplicationMenus getAppMenu() {
		return appMenu;
	}
	public void setAppMenu(ApplicationMenus appMenu) {
		this.appMenu = appMenu;
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
	public String getStatusAdd() {
		return statusAdd;
	}
	public String getStatusUpdate() {
		return statusUpdate;
	}
	public String getStatusReview() {
		return statusReview;
	}
	public void setStatusAdd(String statusAdd) {
		this.statusAdd = statusAdd;
	}
	public void setStatusUpdate(String statusUpdate) {
		this.statusUpdate = statusUpdate;
	}
	public void setStatusReview(String statusReview) {
		this.statusReview = statusReview;
	}
	public String getStatusDeactive() {
		return statusDeactive;
	}
	public String getStatus() {
		return status;
	}
	public void setStatusDeactive(String statusDeactive) {
		this.statusDeactive = statusDeactive;
	}
	public void setStatus(String status) {
		this.status = status;
	}


	public String getControllerName() {
		return controllerName;
	}


	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
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


	public boolean isLinkStatus() {
		return linkStatus;
	}


	public void setLinkStatus(boolean linkStatus) {
		this.linkStatus = linkStatus;
	}
	
	

}
