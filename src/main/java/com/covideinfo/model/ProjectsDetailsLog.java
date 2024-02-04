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

@Entity
@Table(name="projects_details_log")
public class ProjectsDetailsLog implements Serializable{
	
	private static final long serialVersionUID = -5352143631043929987L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="projects_details_log_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long projectDetailsLogId;
	@Column(name = "projectNo")
	private String projectNo;
	@Column(name = "fieldName",columnDefinition = "varchar(10000) default ''")
	private String fieldName;
	@Column(name = "fieldValue" ,columnDefinition = "varchar(10000) default ''")
	private String fieldValue;
	@Column(name = "type")
	private String type;     // study, period, dosing, sampleTimePoins,vitalTimePoints,mealsTimePoints
	@Column(name = "rowNo")
	private Integer rowNo;
	@Column(name = "subRowNo")
	private Integer subRowNo;
	
	@ManyToOne
	@JoinColumn
	private StatusMaster statusId;
	@ManyToOne
	@JoinColumn
	private ProjectsDetails projectDeatailsId;
	
	private String createdBy = "System";
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn = new Date();
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getProjectDetailsLogId() {
		return projectDetailsLogId;
	}
	public void setProjectDetailsLogId(long projectDetailsLogId) {
		this.projectDetailsLogId = projectDetailsLogId;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Integer getRowNo() {
		return rowNo;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
	public Integer getSubRowNo() {
		return subRowNo;
	}
	public void setSubRowNo(Integer subRowNo) {
		this.subRowNo = subRowNo;
	}
	public StatusMaster getStatusId() {
		return statusId;
	}
	public void setStatusId(StatusMaster statusId) {
		this.statusId = statusId;
	}
	public ProjectsDetails getProjectDeatailsId() {
		return projectDeatailsId;
	}
	public void setProjectDeatailsId(ProjectsDetails projectDeatailsId) {
		this.projectDeatailsId = projectDeatailsId;
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
