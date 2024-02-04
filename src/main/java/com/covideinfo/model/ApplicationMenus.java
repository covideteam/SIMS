package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="app_menus")
public class ApplicationMenus implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1615835387766609568L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="app_menus_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long id;
	@Column(name="name")
	private String name;
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
	
	
	public ApplicationMenus() {
		
	}
	
	public ApplicationMenus(String name, String createdBy, Date createdOn ,boolean linkStatus) {
		super();
		this.name = name;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.linkStatus =linkStatus;
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
