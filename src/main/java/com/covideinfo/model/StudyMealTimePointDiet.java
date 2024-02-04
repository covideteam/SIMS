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
@Table(name="study_meal_timepoint_diet")
public class StudyMealTimePointDiet implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6857722627534062091L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "study_meal_timepoint_diet_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="study")
	private StudyMaster study;
	@ManyToOne
	@JoinColumn(name="study_period")
	private StudyPeriodMaster studyPeriod;
    @ManyToOne
	@JoinColumn(name="meal_timepoint")
	private MealsTimePoints mealtimePoint;
	@ManyToOne
	@JoinColumn(name="meal_dietplan")
	private MealDietPlan mealDietPlan;
	@Column(name="created_by")
	private String createdBy;
	@Column(name="created_on")
	private Date createdOn;
	@Column(name="reason")
	private String reason;
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
	public StudyMaster getStudy() {
		return study;
	}
	public void setStudy(StudyMaster study) {
		this.study = study;
	}
	public StudyPeriodMaster getStudyPeriod() {
		return studyPeriod;
	}
	public void setStudyPeriod(StudyPeriodMaster studyPeriod) {
		this.studyPeriod = studyPeriod;
	}
	public MealDietPlan getMealDietPlan() {
		return mealDietPlan;
	}
	public void setMealDietPlan(MealDietPlan mealDietPlan) {
		this.mealDietPlan = mealDietPlan;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public MealsTimePoints getMealtimePoint() {
		return mealtimePoint;
	}
	public void setMealtimePoint(MealsTimePoints mealtimePoint) {
		this.mealtimePoint = mealtimePoint;
	}
	
	

}
