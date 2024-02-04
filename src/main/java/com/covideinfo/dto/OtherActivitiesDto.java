package com.covideinfo.dto;

import java.io.Serializable;

public class OtherActivitiesDto implements Serializable, Comparable<OtherActivitiesDto> {
	private static final long serialVersionUID = 3351818821056615759L;
	private int treatmentNo;
	private Long parameterId;
	private Long activityId;
	private String timePoint;
	private Double tpVal;
	public int getTreatmentNo() {
		return treatmentNo;
	}
	public Long getParameterId() {
		return parameterId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTreatmentNo(int treatmentNo) {
		this.treatmentNo = treatmentNo;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public Double getTpVal() {
		return tpVal;
	}
	public void setTpVal(Double tpVal) {
		this.tpVal = tpVal;
	}
	@Override
	public int compareTo(OtherActivitiesDto o) {
		return Double.compare(this.tpVal, o.tpVal);
	}
	
	
	

}
