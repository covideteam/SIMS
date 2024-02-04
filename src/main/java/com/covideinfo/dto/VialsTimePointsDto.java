package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class VialsTimePointsDto implements Serializable {
	
	private String timePoint;
	private String treatment;
	private String periodName;
	private int vialNo;
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getTreatment() {
		return treatment;
	}
	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public int getVialNo() {
		return vialNo;
	}
	public void setVialNo(int vialNo) {
		this.vialNo = vialNo;
	}

}
