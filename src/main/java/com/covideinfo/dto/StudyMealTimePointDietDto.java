package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

public class StudyMealTimePointDietDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 188558620637113982L;
	private Long periodId;
	private Long mealId;
	private Long dietId;
	private String userName;
	private Date createdOn;
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
	public Long getDietId() {
		return dietId;
	}
	public void setDietId(Long dietId) {
		this.dietId = dietId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	

}
