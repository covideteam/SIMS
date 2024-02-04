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
@Table(name="study_sample_storage_details")
public class StudySampleStorageDetails implements Serializable {
	

	private static final long serialVersionUID = -3567107307536354974L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_sample_storage_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="storage_condition")
	private ConditionParameter storageCondition;
	@Column(name="allowedtime")
	private int allowedTime;
	@Column(name="temperature")
	private int temperature;
	@ManyToOne
	@JoinColumn(name="timepoint_condition")
	private ConditionParameter timePointCondition;
	@ManyToOne
	@JoinColumn(name="storage_id")
	private StudySampleStorage storageId;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="createdOn")
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
	public ConditionParameter getStorageCondition() {
		return storageCondition;
	}
	public int getAllowedTime() {
		return allowedTime;
	}
	public int getTemperature() {
		return temperature;
	}
	public ConditionParameter getTimePointCondition() {
		return timePointCondition;
	}
	public StudySampleStorage getStorageId() {
		return storageId;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStorageCondition(ConditionParameter storageCondition) {
		this.storageCondition = storageCondition;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public void setTimePointCondition(ConditionParameter timePointCondition) {
		this.timePointCondition = timePointCondition;
	}
	public void setStorageId(StudySampleStorage storageId) {
		this.storageId = storageId;
	}
	public void setCreatedBy(String createdBy) {
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
