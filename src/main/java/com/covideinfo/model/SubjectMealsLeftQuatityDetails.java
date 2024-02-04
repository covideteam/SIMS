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
@Table(name="subject_meals_left_quatity_details")
public class SubjectMealsLeftQuatityDetails implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4548810409392778591L;
	@Id
	@SequenceGenerator(name = "pk_sequence", sequenceName = "subject_meals_left_quatity_details_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	private Long id;
	@ManyToOne
	@JoinColumn(name="study_id")
	private StudyMaster studyId;
	@ManyToOne
	@JoinColumn(name="sub_meal_id")
	private SubjectMealsTimePointsData subMealId;
	@ManyToOne
	@JoinColumn(name="diet_plan_itemid")
	private MealDietPlanItem dietPlanItemId;
	@Column(name="left_quantity")
	private Double leftQuantity;
	@Column(name="left_quantity_calories")
	private Double leftQuantityCalories;
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
	public StudyMaster getStudyId() {
		return studyId;
	}
	public void setStudyId(StudyMaster studyId) {
		this.studyId = studyId;
	}
	
	public SubjectMealsTimePointsData getSubMealId() {
		return subMealId;
	}
	public void setSubMealId(SubjectMealsTimePointsData subMealId) {
		this.subMealId = subMealId;
	}
	public MealDietPlanItem getDietPlanItemId() {
		return dietPlanItemId;
	}
	public void setDietPlanItemId(MealDietPlanItem dietPlanItemId) {
		this.dietPlanItemId = dietPlanItemId;
	}
	public Double getLeftQuantity() {
		return leftQuantity;
	}
	public void setLeftQuantity(Double leftQuantity) {
		this.leftQuantity = leftQuantity;
	}
	public Double getLeftQuantityCalories() {
		return leftQuantityCalories;
	}
	public void setLeftQuantityCalories(Double leftQuantityCalories) {
		this.leftQuantityCalories = leftQuantityCalories;
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
	
	
	

}
