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

@SuppressWarnings("serial")
@Entity
@Table(name="meal_diet_plan")
public class MealDietPlan implements Serializable {
	
	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="meal_diet_plan_id_seq",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@Column(name="mealTitle")
	private String mealTitle;
	@ManyToOne
	@JoinColumn(name="mealType")
	private FromStaticData mealType;
	
	@Column(name="createdBy")
	private String createdBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@Column(name="updated_by")
	public String updatedBy;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	public Date updatedOn;
	@Column(name="update_reason")
	public String updateReason;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getMealTitle() {
		return mealTitle;
	}
	public void setMealTitle(String mealTitle) {
		this.mealTitle = mealTitle;
	}
	
	public FromStaticData getMealType() {
		return mealType;
	}
	public void setMealType(FromStaticData mealType) {
		this.mealType = mealType;
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
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	
	
	

}
