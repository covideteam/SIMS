package com.covideinfo.dto;

import java.io.Serializable;

public class SubjectMealTimePointsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 750001059000443381L;
	private Long id;
	private String subjectNo;
	private Long mealId;
	private String timePoint;
	private String sign;
	private String  period;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public Long getMealId() {
		return mealId;
	}
	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	

}
