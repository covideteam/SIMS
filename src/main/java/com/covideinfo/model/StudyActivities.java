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

/*@SuppressWarnings("serial")*/
@Entity
@Table(name="study_activites")
public class StudyActivities implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -291015818195175982L;
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="study_activites_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name="suty_activity")
	private long id;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster sm;
	@ManyToOne
	@JoinColumn(name="study_period")
	private StudyPeriodMaster studyPeriod;
	@ManyToOne
	@JoinColumn(name="activity_id")
	private GlobalActivity activityId;
	@ManyToOne
	@JoinColumn(name="treatment")
	private TreatmentInfo treatment;
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
	public void setId(long id) {
		this.id = id;
	}
	public StudyMaster getSm() {
		return sm;
	}
	public void setSm(StudyMaster sm) {
		this.sm = sm;
	}
	public GlobalActivity getActivityId() {
		return activityId;
	}
	public void setActivityId(GlobalActivity activityId) {
		this.activityId = activityId;
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
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public StudyPeriodMaster getStudyPeriod() {
		return studyPeriod;
	}
	public void setStudyPeriod(StudyPeriodMaster studyPeriod) {
		this.studyPeriod = studyPeriod;
	}
	public TreatmentInfo getTreatment() {
		return treatment;
	}
	public void setTreatment(TreatmentInfo treatment) {
		this.treatment = treatment;
	}
	
	

}
