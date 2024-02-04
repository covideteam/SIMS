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
@Table(name="meal_diet_plan_item")
public class MealDietPlanItem implements Serializable{
	
	@Id
	@SequenceGenerator(name="pk_sequence" ,sequenceName="meal_diet_plan_item_id_seq",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	private long id;
	@ManyToOne
	@JoinColumn(name="meal_Diet_Plan")
	private MealDietPlan mealDietPlan;
	@Column(name="itemName")
	private String itemName;
	@Column(name="itemQuantity")
	private double itemQuantity;
	@Column(name="totalCalories")
	private double totalCalories;
	@ManyToOne
	@JoinColumn(name="units")
	private UnitsMaster units;
	
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
	public MealDietPlan getMealDietPlan() {
		return mealDietPlan;
	}
	public void setMealDietPlan(MealDietPlan mealDietPlan) {
		this.mealDietPlan = mealDietPlan;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public double getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public double getTotalCalories() {
		return totalCalories;
	}
	public void setTotalCalories(double totalCalories) {
		this.totalCalories = totalCalories;
	}
	public UnitsMaster getUnits() {
		return units;
	}
	public void setUnits(UnitsMaster units) {
		this.units = units;
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
