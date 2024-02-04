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

import org.springframework.format.annotation.DateTimeFormat;

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="Study_Activity_Data_Discrepancy")
public class StudyActivityDataDetailsDiscrepancy implements Serializable {
	
	
	private static final long serialVersionUID = 4602457700476463769L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Study_Discrepancy_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name = "comments")
	private String comments;
	@Column(name = "response")
	private String response;
	@Column(name = "status")
	private String status;  //open , inprogress ,close
	
	@ManyToOne
	@JoinColumn
	private StudyActivityData studyActionData;
	
	@ManyToOne
	@JoinColumn
	private StudyCheckInActivityDataDetails studyCheckInActivityData;
	
	@ManyToOne
	@JoinColumn
	private StudyCheckOutActivityDataDetails studyCheckOutActivityData;
	
	@ManyToOne
	@JoinColumn
	private StudyExecutionActivityDataDetails studyExecutionActivityData;
	
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	private String updateBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date updateOn;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public StudyActivityData getStudyActionData() {
		return studyActionData;
	}
	public void setStudyActionData(StudyActivityData studyActionData) {
		this.studyActionData = studyActionData;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
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
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateOn() {
		return updateOn;
	}
	public void setUpdateOn(Date updateOn) {
		this.updateOn = updateOn;
	}
	public StudyCheckInActivityDataDetails getStudyCheckInActivityData() {
		return studyCheckInActivityData;
	}
	public void setStudyCheckInActivityData(StudyCheckInActivityDataDetails studyCheckInActivityData) {
		this.studyCheckInActivityData = studyCheckInActivityData;
	}
	public StudyCheckOutActivityDataDetails getStudyCheckOutActivityData() {
		return studyCheckOutActivityData;
	}
	public void setStudyCheckOutActivityData(StudyCheckOutActivityDataDetails studyCheckOutActivityData) {
		this.studyCheckOutActivityData = studyCheckOutActivityData;
	}
	public StudyExecutionActivityDataDetails getStudyExecutionActivityData() {
		return studyExecutionActivityData;
	}
	public void setStudyExecutionActivityData(StudyExecutionActivityDataDetails studyExecutionActivityData) {
		this.studyExecutionActivityData = studyExecutionActivityData;
	}

	
}
