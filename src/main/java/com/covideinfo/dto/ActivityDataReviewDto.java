package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ActivityDataReviewDto implements Serializable {
	private String registrationNumber;
	private Long activityDataId;
	private String subjectNumber;
	private String type;
	private String createdBy;
	private String createdOn;
	
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public Long getActivityDataId() {
		return activityDataId;
	}
	public void setActivityDataId(Long activityDataId) {
		this.activityDataId = activityDataId;
	}
	public String getSubjectNumber() {
		return subjectNumber;
	}
	public void setSubjectNumber(String subjectNumber) {
		this.subjectNumber = subjectNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
}
