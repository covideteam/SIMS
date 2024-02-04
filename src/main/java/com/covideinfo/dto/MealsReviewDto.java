package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class MealsReviewDto implements Serializable {
	
	private String timePoint;
	private Long mealsDataId;
	private String periodName;
	private String startTime;
	private String endTime;
	private String consumption;
	private String comments;
	public Long getMealsDataId() {
		return mealsDataId;
	}
	public void setMealsDataId(Long mealsDataId) {
		this.mealsDataId = mealsDataId;
	}
	
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getConsumption() {
		return consumption;
	}
	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	
	
	
	

}
