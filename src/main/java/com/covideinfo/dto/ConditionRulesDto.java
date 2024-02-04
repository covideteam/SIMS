package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConditionRulesDto implements Serializable {
	
	private String sourceActivityName;
	private Long sourceActivityId;
	private String ruleType;
	private String sourceParameterName;
	private Long sourceParameterId;
	private String condition;
	private String firstValue;
	private String secondValue;
	private String lsgvName;
	private Long   globalValueId;
	private String lsMessage;
	public String getSourceActivityName() {
		return sourceActivityName;
	}
	public void setSourceActivityName(String sourceActivityName) {
		this.sourceActivityName = sourceActivityName;
	}
	public Long getSourceActivityId() {
		return sourceActivityId;
	}
	public void setSourceActivityId(Long sourceActivityId) {
		this.sourceActivityId = sourceActivityId;
	}
	public String getSourceParameterName() {
		return sourceParameterName;
	}
	public void setSourceParameterName(String sourceParameterName) {
		this.sourceParameterName = sourceParameterName;
	}
	public Long getSourceParameterId() {
		return sourceParameterId;
	}
	public void setSourceParameterId(Long sourceParameterId) {
		this.sourceParameterId = sourceParameterId;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public String getFirstValue() {
		return firstValue;
	}
	public void setFirstValue(String firstValue) {
		this.firstValue = firstValue;
	}
	public String getSecondValue() {
		return secondValue;
	}
	public void setSecondValue(String secondValue) {
		this.secondValue = secondValue;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getLsgvName() {
		return lsgvName;
	}
	public String getLsMessage() {
		return lsMessage;
	}
	public void setLsgvName(String lsgvName) {
		this.lsgvName = lsgvName;
	}
	public void setLsMessage(String lsMessage) {
		this.lsMessage = lsMessage;
	}
	public Long getGlobalValueId() {
		return globalValueId;
	}
	public void setGlobalValueId(Long globalValueId) {
		this.globalValueId = globalValueId;
	}
	
	

}
