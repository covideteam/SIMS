package com.covideinfo.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "study_activity_timepoints_completiondata")
public class StudyActivityTimePointsCompletionData implements Serializable {
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_activity_timepoints_completiondata_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="study_activity_id")
	private Long studyActivityId;
	@Column(name="study_activity_timepoint_id")
	private Long stuydActTimePointId;
	@Column(name="period_id")
	private Long periodId;
	@Column(name="treatment_id")
	private Long treatmentId;
	@Column(name="study_id")
	private Long studyId;
	@Column(name="status")
	private String status; //Completed
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
	public Long getStudyActivityId() {
		return studyActivityId;
	}
	public Long getStuydActTimePointId() {
		return stuydActTimePointId;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public Long getStudyId() {
		return studyId;
	}
	public String getStatus() {
		return status;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}
	public void setStuydActTimePointId(Long stuydActTimePointId) {
		this.stuydActTimePointId = stuydActTimePointId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public void setStatus(String status) {
		this.status = status;
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
