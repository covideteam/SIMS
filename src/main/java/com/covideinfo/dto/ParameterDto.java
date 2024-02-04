package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ParameterDto implements Serializable {
	private Long activityId;
	private Long parameterId;
	private String parameterName;
	private int paramOrder;
	private String value;
	
	private Long studyActivityDataParameterId;
	
	public Long getParameterId() {
		return parameterId;
	}
	public String getParameterName() {
		return parameterName;
	}
	public int getParamOrder() {
		return paramOrder;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public void setParamOrder(int paramOrder) {
		this.paramOrder = paramOrder;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getStudyActivityDataParameterId() {
		return studyActivityDataParameterId;
	}
	public void setStudyActivityDataParameterId(Long studyActivityDataParameterId) {
		this.studyActivityDataParameterId = studyActivityDataParameterId;
	}
	
	

}
