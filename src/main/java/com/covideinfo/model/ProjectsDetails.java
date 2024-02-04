package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="projects_details")
public class ProjectsDetails implements Serializable, Comparable<ProjectsDetails> {
	
	private static final long serialVersionUID = 1150344494022795891L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="projects_details_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private Long projectDetailsId;
    @Column(name = "fieldName")
	private String fieldName;
    @Column(columnDefinition = "varchar(10000) default ''")
	private String fieldValue;
	@Column(name = "type")
	private String type;     // study, period, dosing, dosingParameters, sampleTimePoins,vitalTimePoints,mealsTimePoints
	@Column(name = "rowNo")
	private Integer rowNo = 0;
	@Column(name = "subRowNo")
	private Integer subRowNo = 0;
	@Column(name = "status")
	private boolean status = true;
	@Column(name = "fieldOrder")
    private Integer fieldOrder = 0;
	
	@Column(name = "displayValue", columnDefinition = "varchar(10000) default ''")
	private String displayValue; 
	@Column(name="treatment_no")
	private Long treatmentNo;
	
	@ManyToOne
	@JoinColumn
	private Projects projectsId;
	
	@ManyToOne
	@JoinColumn
	private StatusMaster statusid;
	
	
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	@Transient
	private List<String> timePoints;
	
	public List<String> getTimePoints() {
		return timePoints;
	}
	public void setTimePoints(List<String> timePoints) {
		this.timePoints = timePoints;
	}
	public Long getProjectDetailsId() {
		return projectDetailsId;
	}
	public void setProjectDetailsId(Long projectDetailsId) {
		this.projectDetailsId = projectDetailsId;
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
	
	public int getRowNo() {
		return rowNo;
	}
	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	public int getSubRowNo() {
		return subRowNo;
	}
	public void setSubRowNo(int subRowNo) {
		this.subRowNo = subRowNo;
	}
	public StatusMaster getStatusid() {
		return statusid;
	}
	public void setStatusid(StatusMaster statusid) {
		this.statusid = statusid;
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
	public Projects getProjectsId() {
		return projectsId;
	}
	public void setProjectsId(Projects projectsId) {
		this.projectsId = projectsId;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
	public void setSubRowNo(Integer subRowNo) {
		this.subRowNo = subRowNo;
	}
	public Integer getFieldOrder() {
		return fieldOrder;
	}
	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	@Override
	public int compareTo(ProjectsDetails o) {
		return this.fieldOrder = o.getFieldOrder();
	}
	public Long getTreatmentNo() {
		return treatmentNo;
	}
	public void setTreatmentNo(Long treatmentNo) {
		this.treatmentNo = treatmentNo;
	}
	@Override
	public String toString() {
		return "ProjectsDetails [projectDetailsId=" + projectDetailsId + ", fieldName=" + fieldName + ", fieldValue="
				+ fieldValue + ", type=" + type + ", rowNo=" + rowNo + ", subRowNo=" + subRowNo + ", status=" + status
				+ ", fieldOrder=" + fieldOrder + ", displayValue=" + displayValue + ", treatmentNo=" + treatmentNo
				+ ", projectsId=" + projectsId + ", statusid=" + statusid + ", createdBy=" + createdBy + ", createdOn="
				+ createdOn + ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", updateReason=" + updateReason
				+ ", timePoints=" + timePoints + "]";
	}
	
	
}
