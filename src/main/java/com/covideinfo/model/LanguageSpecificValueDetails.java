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

@Entity
@Table(name="language_specific_value_details")
public class LanguageSpecificValueDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6976807702415215657L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="language_specific_value_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="name")
	private String name;
	@ManyToOne
	@JoinColumn(name="lang_id")
	private InternationalizaionLanguages inlagId;
	@ManyToOne
	@JoinColumn(name="global_val_id")
	private GlobalValues globalValId;
	@ManyToOne
    @JoinColumn(name="created_by")
    private UserMaster createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public InternationalizaionLanguages getInlagId() {
		return inlagId;
	}
	public GlobalValues getGlobalValId() {
		return globalValId;
	}
	public UserMaster getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setInlagId(InternationalizaionLanguages inlagId) {
		this.inlagId = inlagId;
	}
	public void setGlobalValId(GlobalValues globalValId) {
		this.globalValId = globalValId;
	}
	public void setCreatedBy(UserMaster createdBy) {
		this.createdBy = createdBy;
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
