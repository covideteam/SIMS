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
@Table(name = "Randomization_File_Status")
public class RandomizationFileStatus implements Serializable {
	
	
	private static final long serialVersionUID = -6009896726842604384L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="Randomization_File_Status_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="projectId")
	private long projectId;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusMaster status;//INREVIEW,APPROVED
	@Column(name="noofSubject")
	private String noofSubject;
	@Column(name="noofPeriod")
	private String noofPeriod;
	@Column(name="codeType")
	private String codeType;
	
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="update_by")
	private String updateBy;
	@Column(name="update_on")
	private Date updateOn;
	@Column(name="update_reason")
	public String updateReason;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
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
	
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public String getNoofSubject() {
		return noofSubject;
	}
	public void setNoofSubject(String noofSubject) {
		this.noofSubject = noofSubject;
	}
	public String getNoofPeriod() {
		return noofPeriod;
	}
	public void setNoofPeriod(String noofPeriod) {
		this.noofPeriod = noofPeriod;
	}
	public String getCodeType() {
		return codeType;
	}
	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}
	

	
	
}
