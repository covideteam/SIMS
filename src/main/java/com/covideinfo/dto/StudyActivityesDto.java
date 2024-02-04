package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class StudyActivityesDto implements Serializable {
	
	private Long activityId;
	private String treatmentName;
	private List<Long> parameterIds;
	private Map<String, List<Long>> paramMap;
	public Long getActivityId() {
		return activityId;
	}
	public List<Long> getParameterIds() {
		return parameterIds;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public void setParameterIds(List<Long> parameterIds) {
		this.parameterIds = parameterIds;
	}
	public String getTreatmentName() {
		return treatmentName;
	}
	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}
	public Map<String, List<Long>> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, List<Long>> paramMap) {
		this.paramMap = paramMap;
	}
	
	
	
	

}
