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
@Table(name="dispensed_ip_handover")
public class DispensedIPHandover implements Serializable {
	
	private static final long serialVersionUID = -5480702676945050733L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="dispensed_ip_handover_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="projectId")
	private Projects projectId;
	@ManyToOne
	@JoinColumn(name="periodId")
	private StudyPeriodMaster periodId;
	@Column(name="sealedCode")
	private String sealedCode; /*(applicable to blinded studies)*/
	
	@Column(name="created_by")
	private String createdBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date updatedOn;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Projects getProjectId() {
		return projectId;
	}
	public void setProjectId(Projects projectId) {
		this.projectId = projectId;
	}
	public StudyPeriodMaster getPeriodId() {
		return periodId;
	}
	public void setPeriodId(StudyPeriodMaster periodId) {
		this.periodId = periodId;
	}
	public String getSealedCode() {
		return sealedCode;
	}
	public void setSealedCode(String sealedCode) {
		this.sealedCode = sealedCode;
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
	
	
	
}
