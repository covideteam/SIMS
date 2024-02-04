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

//@SuppressWarnings("serial")
@Entity
@Table(name = "study_period_master")
public class StudyPeriodMaster implements Serializable, Comparable<StudyPeriodMaster> {

	
	
	private static final long serialVersionUID = -4824701202638593493L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_period_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@Column(name = "periodId")
	private Long id;
	@ManyToOne
	@JoinColumn(name = "studyId")
	private StudyMaster study;
	private String periodName;
	private String periodDesc;
	private int periodNo = 0;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@Column(name="updated_on")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public Long getId() {
		return id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public String getPeriodName() {
		return periodName;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
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
	@Override
	public int compareTo(StudyPeriodMaster o) {
		return ((Integer) this.getPeriodNo()).compareTo((Integer) o.getPeriodNo());
	}
	public String getPeriodDesc() {
		return periodDesc;
	}
	public void setPeriodDesc(String periodDesc) {
		this.periodDesc = periodDesc;
	}
	
	
	
}
