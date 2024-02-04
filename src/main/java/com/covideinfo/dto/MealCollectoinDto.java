package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class MealCollectoinDto implements Serializable{

	private static final long serialVersionUID = -1357871377706612745L;
	private Long studyId;
	private List<String> startMealsData;
	private List<String> endMealsData;
	private List<String> subNoList;
	private List<Long> periodIds;
	private List<Long> timePointIds;
//	private String runningTimeWithSeconds, ranningTime;
	private boolean timeDeviation= false;
	private List<String> timeDeviationTime;
	private Long currentPeriod;
	
	
	public Long getStudyId() {
		return studyId;
	}
	public List<String> getStartMealsData() {
		return startMealsData;
	}
	public List<String> getEndMealsData() {
		return endMealsData;
	}
	/*public String getRunningTimeWithSeconds() {
		return runningTimeWithSeconds;
	}
	public String getRanningTime() {
		return ranningTime;
	}*/
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public void setStartMealsData(List<String> startMealsData) {
		this.startMealsData = startMealsData;
	}
	public void setEndMealsData(List<String> endMealsData) {
		this.endMealsData = endMealsData;
	}
	/*public void setRunningTimeWithSeconds(String runningTimeWithSeconds) {
		this.runningTimeWithSeconds = runningTimeWithSeconds;
	}
	public void setRanningTime(String ranningTime) {
		this.ranningTime = ranningTime;
	}*/
	public List<String> getSubNoList() {
		return subNoList;
	}
	public List<Long> getPeriodIds() {
		return periodIds;
	}
	public void setSubNoList(List<String> subNoList) {
		this.subNoList = subNoList;
	}
	public void setPeriodIds(List<Long> periodIds) {
		this.periodIds = periodIds;
	}
	public List<Long> getTimePointIds() {
		return timePointIds;
	}
	public void setTimePointIds(List<Long> timePointIds) {
		this.timePointIds = timePointIds;
	}
	public boolean isTimeDeviation() {
		return timeDeviation;
	}
	
	public void setTimeDeviation(boolean timeDeviation) {
		this.timeDeviation = timeDeviation;
	}
	public List<String> getTimeDeviationTime() {
		return timeDeviationTime;
	}
	public void setTimeDeviationTime(List<String> timeDeviationTime) {
		this.timeDeviationTime = timeDeviationTime;
	}
	public Long getCurrentPeriod() {
		return currentPeriod;
	}
	public void setCurrentPeriod(Long currentPeriod) {
		this.currentPeriod = currentPeriod;
	}
	
	
		
}
