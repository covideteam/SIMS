package com.covideinfo.dto;

import java.io.Serializable;

public class StudySampleStorageDetailsDto implements Serializable {
	
	private static final long serialVersionUID = -970340096145401446L;
	private Long storageCondition;
	private int allowedTime;
	private int temperature;
	private Long timePointCondition;
	public Long getStorageCondition() {
		return storageCondition;
	}
	public int getAllowedTime() {
		return allowedTime;
	}
	public int getTemperature() {
		return temperature;
	}
	public Long getTimePointCondition() {
		return timePointCondition;
	}
	public void setStorageCondition(Long storageCondition) {
		this.storageCondition = storageCondition;
	}
	public void setAllowedTime(int allowedTime) {
		this.allowedTime = allowedTime;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public void setTimePointCondition(Long timePointCondition) {
		this.timePointCondition = timePointCondition;
	}
	
	

}
