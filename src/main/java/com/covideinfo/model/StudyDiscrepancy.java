package com.covideinfo.model;

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

@Entity
@Table(name="Project_Details_Discrepancy")
public class StudyDiscrepancy {
	/*@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Project_Details_Discrepancy_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="pk_sequence")*/
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Project_Details_Discrepancy_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	
	@Column(name = "comments")
	private String comments;
	@Column(name = "response")
	private String response;
	@Column(name = "status")
	private String status;  //open , inprogress ,close, deleted
	@Column(name = "isResponseSubmitted")
	private Boolean isResponseSubmitted = false;
	
	@ManyToOne
	@JoinColumn
	private Projects project;
	
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
	public Projects getProject() {
		return project;
	}
	public void setProject(Projects project) {
		this.project = project;
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
	public boolean isResponseSubmitted() {
		return isResponseSubmitted;
	}
	public void setResponseSubmitted(boolean isResponseSubmitted) {
		this.isResponseSubmitted = isResponseSubmitted;
	}
	
}
