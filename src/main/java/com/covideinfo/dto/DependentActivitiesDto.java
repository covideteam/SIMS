package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DependentActivitiesDto implements Serializable {
	
	private Long activityId;
	private String activityName;
	private String ruleType;
	private List<ActivityDetailsDto> dependentActList;
	public Long getActivityId() {
		return activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public String getRuleType() {
		return ruleType;
	}
	public List<ActivityDetailsDto> getDependentActList() {
		return dependentActList;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public void setDependentActList(List<ActivityDetailsDto> dependentActList) {
		this.dependentActList = dependentActList;
	}
	
	
	
	
	
	
	
	

}
