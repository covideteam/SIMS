package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StudyActivityTimpointsSavingDto implements Serializable, Comparable<StudyActivityTimpointsSavingDto> {
	
	private String timePoint;
	private String windowperiodSign;
	private int windowPeriod;
	private String windowPeriodType;
	private String position;
	private String orthoStatic;
	private String orthoStaticPosition;
	private int treatmentNo;
	private String parameters;
	private String sign;
	private Double satsTp;
	
	public String getTimePoint() {
		return timePoint;
	}
	public String getWindowperiodSign() {
		return windowperiodSign;
	}
	public int getWindowPeriod() {
		return windowPeriod;
	}
	public String getWindowPeriodType() {
		return windowPeriodType;
	}
	public String getPosition() {
		return position;
	}
	
	public String getOrthoStaticPosition() {
		return orthoStaticPosition;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public void setWindowperiodSign(String windowperiodSign) {
		this.windowperiodSign = windowperiodSign;
	}
	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}
	public void setWindowPeriodType(String windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	public void setOrthoStaticPosition(String orthoStaticPosition) {
		this.orthoStaticPosition = orthoStaticPosition;
	}
	
	public int getTreatmentNo() {
		return treatmentNo;
	}
	public void setTreatmentNo(int treatmentNo) {
		this.treatmentNo = treatmentNo;
	}
	@Override
	public int compareTo(StudyActivityTimpointsSavingDto o) {
		return this.treatmentNo = o.treatmentNo;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getOrthoStatic() {
		return orthoStatic;
	}
	public void setOrthoStatic(String orthoStatic) {
		this.orthoStatic = orthoStatic;
	}
	public Double getSatsTp() {
		return satsTp;
	}
	public void setSatsTp(Double satsTp) {
		this.satsTp = satsTp;
	}
	
	

}
