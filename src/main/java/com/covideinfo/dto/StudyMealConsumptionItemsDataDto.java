package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class StudyMealConsumptionItemsDataDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8120226510990991977L;
	private Long projectId;
	private Long mealId;
	private List<String> mealDietConfigData;
	private Double totalLeftCalories;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getMealId() {
		return mealId;
	}
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	public List<String> getMealDietConfigData() {
		return mealDietConfigData;
	}
	public void setMealDietConfigData(List<String> mealDietConfigData) {
		this.mealDietConfigData = mealDietConfigData;
	}
	public Double getTotalLeftCalories() {
		return totalLeftCalories;
	}
	public void setTotalLeftCalories(Double totalLeftCalories) {
		this.totalLeftCalories = totalLeftCalories;
	}
	
	

}
