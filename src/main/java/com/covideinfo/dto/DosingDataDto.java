package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DosingDataDto implements Serializable {
	
	private Long dosingDataId;
	private String timePoint;
	private String periodName;
	private String dosingTime;
	private List<ParameterFormDataDto> parameterDto;
	public Long getDosingDataId() {
		return dosingDataId;
	}
	public void setDosingDataId(Long dosingDataId) {
		this.dosingDataId = dosingDataId;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getDosingTime() {
		return dosingTime;
	}
	public void setDosingTime(String dosingTime) {
		this.dosingTime = dosingTime;
	}
	public List<ParameterFormDataDto> getParameterDto() {
		return parameterDto;
	}
	public void setParameterDto(List<ParameterFormDataDto> parameterDto) {
		this.parameterDto = parameterDto;
	}
	
	

}
