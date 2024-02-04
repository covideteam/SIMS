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

@Entity
@Table(name="dosing_Info_Data")
public class DosingInfoData {
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="dosing_Info_Data_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long Id;
	@ManyToOne
	@JoinColumn(name="dosingInfoId")
	private DosingInfo dosingInfo;
	private Long projectId;
	@Column(name = "projectNo")
	private String projectNo;
	@Column(name = "type")
	private String type;
	@Column(name="treatment")
	private String treatment;
	@Column(name="timepoint")
	private String timepoint;
	@Column(name="differenceBetweenSubject")
	private int differenceBetweenSubjects;
	@Column(name="created_by")
	private String createdBy;
	@Column(name ="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name ="updated_on")
	private Date updatedOn;
	@Column(name="reason")
	private String reason;
	
	public DosingInfo getDosingInfo() {
		return dosingInfo;
	}
	public void setDosingInfo(DosingInfo dosingInfo) {
		this.dosingInfo = dosingInfo;
	}
	public long getId() {
		return Id;
	}
	public void setId(long id) {
		Id = id;
	}
	
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getTimepoint() {
		return timepoint;
	}
	public void setTimepoint(String timepoint) {
		this.timepoint = timepoint;
	}
	public int getDifferenceBetweenSubjects() {
		return differenceBetweenSubjects;
	}
	public void setDifferenceBetweenSubjects(int differenceBetweenSubjects) {
		this.differenceBetweenSubjects = differenceBetweenSubjects;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	
}
