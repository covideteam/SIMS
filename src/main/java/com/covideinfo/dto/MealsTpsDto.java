package com.covideinfo.dto;

import java.io.Serializable;

public class MealsTpsDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5125984596656613555L;
	private Long id;
	private String tpSign;
	private String timePoint;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	

}
