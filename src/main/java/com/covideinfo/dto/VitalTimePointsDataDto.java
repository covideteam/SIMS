package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class VitalTimePointsDataDto implements Serializable {
	
	private Long vitalDataId;
	private String periodName;
	private String timePoint;
	private String startTime;
	private String endTime;
	private List<ParameterFormDataDto> parameterDto;
	public Long getVitalDataId() {
		return vitalDataId;
	}
	public void setVitalDataId(Long vitalDataId) {
		this.vitalDataId = vitalDataId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
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
	public List<ParameterFormDataDto> getParameterDto() {
		return parameterDto;
	}
	public void setParameterDto(List<ParameterFormDataDto> parameterDto) {
		this.parameterDto = parameterDto;
	}
	
	

}
