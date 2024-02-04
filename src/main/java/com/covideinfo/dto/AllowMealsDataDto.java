package com.covideinfo.dto;

import java.io.Serializable;

public class AllowMealsDataDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -244086810378149251L;
	private Long projectId;
	private Long periodId;
	private Long mealId;
	private String subjects;
	private int allowedTime;
	private String reason;
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public Long getMealId() {
		return mealId;
	}
	public void setMealId(Long mealId) {
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
	
	

}
