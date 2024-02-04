package com.covideinfo.dto;

import java.io.Serializable;

public class MealDietPlanDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1348692497732819303L;
	private Long id;
	private String mealTitle;
	private String mealType;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
