package com.covideinfo.dto;

import java.io.Serializable;

public class StudyActivityTimpointsSavingDummyDto implements Serializable, Comparable<StudyActivityTimpointsSavingDummyDto> {

	private static final long serialVersionUID = 1925279173508611720L;
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
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getWindowperiodSign() {
		return windowperiodSign;
	}
	public void setWindowperiodSign(String windowperiodSign) {
		this.windowperiodSign = windowperiodSign;
	}
	public int getWindowPeriod() {
		return windowPeriod;
	}
	public void setWindowPeriod(int windowPeriod) {
		this.windowPeriod = windowPeriod;
	}
	public String getWindowPeriodType() {
		return windowPeriodType;
	}
	public void setWindowPeriodType(String windowPeriodType) {
		this.windowPeriodType = windowPeriodType;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getOrthoStatic() {
		return orthoStatic;
	}
	public void setOrthoStatic(String orthoStatic) {
		this.orthoStatic = orthoStatic;
	}
	public String getOrthoStaticPosition() {
		return orthoStaticPosition;
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
	public Double getSatsTp() {
		return satsTp;
	}
	public void setSatsTp(Double satsTp) {
		this.satsTp = satsTp;
	}
	@Override
	public int compareTo(StudyActivityTimpointsSavingDummyDto o) {
		return Double.compare(this.satsTp, o.satsTp);
	}
	
	
}
