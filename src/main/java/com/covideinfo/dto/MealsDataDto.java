package com.covideinfo.dto;

import java.io.Serializable;

public class MealsDataDto implements Serializable {

	private static final long serialVersionUID = -5721194375609127450L;
	private Long timepointId;
	private String timePoint;
	private String startTime;
	private String endTime;
	public Long getTimepointId() {
		return timepointId;
	}
	public void setTimepointId(Long timepointId) {
		this.timepointId = timepointId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
