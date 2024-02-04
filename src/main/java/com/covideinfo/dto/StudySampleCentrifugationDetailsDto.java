package com.covideinfo.dto;

import java.io.Serializable;

public class StudySampleCentrifugationDetailsDto implements Serializable {
	
	private static final long serialVersionUID = -4827198216637971790L;
	private String applicableTo;
	private int centrifugationSpeed;
	private int centrifugationProcessTime;
	private int centrifugationTemparature;
	private int centrifugationAllowedTime;
	private String conditions;
	public String getApplicableTo() {
		return applicableTo;
	}
	public int getCentrifugationSpeed() {
		return centrifugationSpeed;
	}
	public int getCentrifugationProcessTime() {
		return centrifugationProcessTime;
	}
	public int getCentrifugationTemparature() {
		return centrifugationTemparature;
	}
	public int getCentrifugationAllowedTime() {
		return centrifugationAllowedTime;
	}
	public String getConditions() {
		return conditions;
	}
	public void setApplicableTo(String applicableTo) {
		this.applicableTo = applicableTo;
	}
	public void setCentrifugationSpeed(int centrifugationSpeed) {
		this.centrifugationSpeed = centrifugationSpeed;
	}
	public void setCentrifugationProcessTime(int centrifugationProcessTime) {
		this.centrifugationProcessTime = centrifugationProcessTime;
	}
	public void setCentrifugationTemparature(int centrifugationTemparature) {
		this.centrifugationTemparature = centrifugationTemparature;
	}
	public void setCentrifugationAllowedTime(int centrifugationAllowedTime) {
		this.centrifugationAllowedTime = centrifugationAllowedTime;
	}
	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	

}
