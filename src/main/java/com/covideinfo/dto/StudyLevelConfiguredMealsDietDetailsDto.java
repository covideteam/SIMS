package com.covideinfo.dto;

import java.io.Serializable;

public class StudyLevelConfiguredMealsDietDetailsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7943782014946231023L;
	private Long id;
	//Meals
	private Long mealId;
	private String tpSign;
	private String timePoint;
	//Period
	private Long periodId;
	private String periodName;
	//MealDietPlanItems
	private Long mealDietId;
	private String mealTitle;
	private String mealType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMealId() {
		return mealId;
	}
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	public String getTpSign() {
		return tpSign;
	}
	public void setTpSign(String tpSign) {
		this.tpSign = tpSign;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Long getMealDietId() {
		return mealDietId;
	}
	public void setMealDietId(Long mealDietId) {
		this.mealDietId = mealDietId;
	}
	public String getMealTitle() {
		return mealTitle;
	}
	public void setMealTitle(String mealTitle) {
		this.mealTitle = mealTitle;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	
	
	
	
	

}
