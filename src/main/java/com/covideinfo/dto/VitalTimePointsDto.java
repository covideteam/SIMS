package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class VitalTimePointsDto implements Serializable{
	private long id;
	private String vitalPosition = "";
	private Long treatmentInfoId;
	private String timePoint = "";
	private String timePointType;
	private String sign = "";
	private Long treatment;
	private String windowPeriodSign = "";  //PLUSANDMINUS/PLUS/MINUS
	private int windowPeriod;
	private String windowPeriodType = "";  //MINUTES, HOURS
	private int timePointNo;
	private boolean orthostatic;
	private String orthostaticPosition = "";
	private List<Long> parameterIds;
	
	public List<Long> getParameterIds() {
		return parameterIds;
	}
	public void setParameterIds(List<Long> parameterIds) {
		this.parameterIds = parameterIds;
	}
	public Long getTreatment() {
		return treatment;
	}
	public void setTreatment(Long treatment) {
		this.treatment = treatment;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getVitalPosition() {
		return vitalPosition;
	}
	public void setVitalPosition(String vitalPosition) {
		this.vitalPosition = vitalPosition;
	}
	public Long getTreatmentInfoId() {
		return treatmentInfoId;
	}
	public void setTreatmentInfoId(Long treatmentInfoId) {
		this.treatmentInfoId = treatmentInfoId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getTimePointType() {
		return timePointType;
	}
	public void setTimePointType(String timePointType) {
		this.timePointType = timePointType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getWindowPeriodSign() {
		return windowPeriodSign;
	}
	public void setWindowPeriodSign(String windowPeriodSign) {
		this.windowPeriodSign = windowPeriodSign;
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
	public int getTimePointNo() {
		return timePointNo;
	}
	public void setTimePointNo(int timePointNo) {
		this.timePointNo = timePointNo;
	}
	public boolean isOrthostatic() {
		return orthostatic;
	}
	public void setOrthostatic(boolean orthostatic) {
		this.orthostatic = orthostatic;
	}
	public String getOrthostaticPosition() {
		return orthostaticPosition;
	}
	public void setOrthostaticPosition(String orthostaticPosition) {
		this.orthostaticPosition = orthostaticPosition;
	}
	
	
}
