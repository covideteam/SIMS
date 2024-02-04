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

@SuppressWarnings("serial")
@Entity
@Table(name="subject_withdraw_details")
public class SubjectWithdrawDetails implements Serializable {
	
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "subject_withdraw_details_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="study")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="period")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name="global_activity")
	private GlobalActivity globalActivity;
	@ManyToOne
	@JoinColumn(name="study_volunteer")
	private StudyVolunteerReporting studyVolunteer;
	@Column(name="withdraw_type")
	private String withdrawType;
	@Column(name="withdraw_level")
	private String withdrawLevel;
	@Column(name="withdraw_comments", length=10000)
	private String withdrawComments;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	@Column(name="reason")
	private String reason;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public GlobalActivity getGlobalActivity() {
		return globalActivity;
	}
	public void setGlobalActivity(GlobalActivity globalActivity) {
		this.globalActivity = globalActivity;
	}
	public StudyVolunteerReporting getStudyVolunteer() {
		return studyVolunteer;
	}
	public void setStudyVolunteer(StudyVolunteerReporting studyVolunteer) {
		this.studyVolunteer = studyVolunteer;
	}
	public String getWithdrawLevel() {
		return withdrawLevel;
	}
	public void setWithdrawLevel(String withdrawLevel) {
		this.withdrawLevel = withdrawLevel;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getWithdrawComments() {
		return withdrawComments;
	}
	public void setWithdrawComments(String withdrawComments) {
		this.withdrawComments = withdrawComments;
	}
	public String getWithdrawType() {
		return withdrawType;
	}
	public void setWithdrawType(String withdrawType) {
		this.withdrawType = withdrawType;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	
	
	
	
	
	
	
	
	

}
