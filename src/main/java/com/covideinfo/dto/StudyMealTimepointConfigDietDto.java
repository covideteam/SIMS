package com.covideinfo.dto;

import java.io.Serializable;

public class StudyMealTimepointConfigDietDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5994305151601577302L;
	private Long mealId;
	private Long dietPlanId;
	public Long getMealId() {
		return mealId;
	}
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	public Long getDietPlanId() {
		return dietPlanId;
	}
	public void setDietPlanId(Long dietPlanId) {
		this.dietPlanId = dietPlanId;
	}
	

}
