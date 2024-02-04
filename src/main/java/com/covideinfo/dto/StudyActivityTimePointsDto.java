package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StudyActivityTimePointsDto implements Serializable {
	
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
	public String getOrthoStatic() {
		return orthoStatic;
	}
	public String getOrthoStaticPosition() {
		return orthoStaticPosition;
	}
	public int getTreatmentNo() {
		return treatmentNo;
	}
	public String getParameters() {
		return parameters;
	}
	public String getSign() {
		return sign;
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
	public void setOrthoStatic(String orthoStatic) {
		this.orthoStatic = orthoStatic;
	}
	public void setOrthoStaticPosition(String orthoStaticPosition) {
		this.orthoStaticPosition = orthoStaticPosition;
	}
	public void setTreatmentNo(int treatmentNo) {
		this.treatmentNo = treatmentNo;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	

}
