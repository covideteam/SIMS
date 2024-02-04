package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class StudyDesingActivityDetailsDto implements Serializable {
	
	private String activityName;
	private Long activityId;
	private List<StudyDesignParametersDto> parameterDto;
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public List<StudyDesignParametersDto> getParameterDto() {
		return parameterDto;
	}
	public void setParameterDto(List<StudyDesignParametersDto> parameterDto) {
		this.parameterDto = parameterDto;
	}
	
	

}
