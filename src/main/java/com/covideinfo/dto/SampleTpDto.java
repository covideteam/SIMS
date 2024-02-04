package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SampleTpDto implements Serializable {
	
	private Long tpId;
	private String sing;
	private String timePoint;
	private Long treatmentId;
	private String treatmentName;
	public Long getTpId() {
		return tpId;
	}
	public void setTpId(Long tpId) {
		this.tpId = tpId;
	}
	public String getSing() {
		return sing;
	}
	public void setSing(String sing) {
		this.sing = sing;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	
	

}
