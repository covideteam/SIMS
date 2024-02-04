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
@Table(name="study_sample_processing")
public class StudySampleProcessing implements Serializable {
	
	
	private static final long serialVersionUID = 5002303375572394354L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_sample_processing_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="study")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="treatment")
	private TreatmentInfo treatment;
	@Column(name="allowed_time")
	private int allowedTime;
	@Column(name="allowed_timefrom")
	private String allowedTimeFrom;
	@Column(name="conditions")
	private String conditions;
	@Column(name="matrix_different")
	private boolean matrixDifferent;
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
	public long getId() {
		return id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public int getAllowedTime() {
		return allowedTime;
	}
	public String getAllowedTimeFrom() {
		return allowedTimeFrom;
	}
	public String getConditions() {
		return conditions;
	}
	public boolean isMatrixDifferent() {
		return matrixDifferent;
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
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public void setAllowedTimeFrom(String allowedTimeFrom) {
		this.allowedTimeFrom = allowedTimeFrom;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	public void setMatrixDifferent(boolean matrixDifferent) {
		this.matrixDifferent = matrixDifferent;
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
