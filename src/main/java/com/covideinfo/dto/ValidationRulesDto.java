package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ValidationRulesDto implements Serializable {
	
	private String activityName;
	private Long activityId;
	private String ruleType;
	private String sourceParamName;
	private Long sourceParamId;
	private String condition;
	private String message;
	
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
	public String getSourceParamName() {
		return sourceParamName;
	}
	public void setSourceParamName(String sourceParamName) {
		this.sourceParamName = sourceParamName;
	}
	public Long getSourceParamId() {
		return sourceParamId;
	}
	public void setSourceParamId(Long sourceParamId) {
		this.sourceParamId = sourceParamId;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	
	
	
	

}
