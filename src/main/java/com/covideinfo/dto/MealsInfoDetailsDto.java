package com.covideinfo.dto;

import java.io.Serializable;

public class MealsInfoDetailsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3767784558855364889L;
	private Long id;
	private String mealType;
	private String timePoint;
	private String sign;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
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
	
	
	

}
