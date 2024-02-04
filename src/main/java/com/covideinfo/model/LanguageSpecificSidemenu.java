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
@Table(name="language_specific_sidemenu")
public class LanguageSpecificSidemenu implements Serializable {
	
	
	private static final long serialVersionUID = 5959300322861300003L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="language_specific_sidemenu_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="linkname")
	private String linkname;
	@ManyToOne
	@JoinColumn(name="lang_id")
	private InternationalizaionLanguages inlagId;
	@ManyToOne
	@JoinColumn(name="application_Sidemenu")
	private ApplictionSideMenus applictionSideMenus;
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
	public LanguageSpecificSidemenu() {
		
	}
	public LanguageSpecificSidemenu(String linkname, InternationalizaionLanguages inlagId,
			ApplictionSideMenus applictionSideMenus, String createdBy, Date createdOn) {
		super();
		this.linkname = linkname;
		this.inlagId = inlagId;
		this.applictionSideMenus = applictionSideMenus;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLinkname() {
		return linkname;
	}
	public void setLinkname(String linkname) {
		this.linkname = linkname;
	}
	public InternationalizaionLanguages getInlagId() {
		return inlagId;
	}
	public void setInlagId(InternationalizaionLanguages inlagId) {
		this.inlagId = inlagId;
	}
	public ApplictionSideMenus getApplictionSideMenus() {
		return applictionSideMenus;
	}
	public void setApplictionSideMenus(ApplictionSideMenus applictionSideMenus) {
		this.applictionSideMenus = applictionSideMenus;
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
	
	
	
}
