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
@Table(name="allow_study_subject_meals")
public class AllowStudySubjectMeals implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2048557290166699470L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "allow_study_subject_meals_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster studyId;
	@ManyToOne
	@JoinColumn(name="period")
	private StudyPeriodMaster period;
	@ManyToOne
	@JoinColumn(name="meal_id")
	private MealsTimePoints mealId;
	@Column(name="subjects")
	private String subjects;
	@Column(name="allowed_time")
	private int allowedTime;
	@Column(name="reason")
	private String reason;
	@ManyToOne
	@JoinColumn(name="status")
	private StatusMaster status;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="updated_by")
	private String updatedBy;
	@Column(name="updated_on")
	private Date updatedOn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public StudyMaster getStudyId() {
		return studyId;
	}
	public void setStudyId(StudyMaster studyId) {
		this.studyId = studyId;
	}
	public StudyPeriodMaster getPeriod() {
		return period;
	}
	public void setPeriod(StudyPeriodMaster period) {
		this.period = period;
	}
	public MealsTimePoints getMealId() {
		return mealId;
	}
	public void setMealId(MealsTimePoints mealId) {
		this.mealId = mealId;
	}
	public String getSubjects() {
		return subjects;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public int getAllowedTime() {
		return allowedTime;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public StatusMaster getStatus() {
		return status;
	}
	public void setStatus(StatusMaster status) {
		this.status = status;
	}
	
	
	
	
	
	
}
