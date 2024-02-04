package com.covideinfo.dto;

import java.io.Serializable;

public class AllowStudySubjectMealsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1377005512011361264L;
	private Long id;
	private Long periodId;
	private String periodName;
	private String tpSign;
	private String timePoint;
	private String subjects;
	private int allowedTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
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
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	
	
	
	
	
	

}
