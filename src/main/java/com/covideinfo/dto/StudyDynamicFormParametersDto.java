package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;

@SuppressWarnings("serial")
public class StudyDynamicFormParametersDto implements Serializable {
	
	private Long activityId;
	private Long studyActivityId;
	private List<LanguageSpecificGlobalParameterDetails> parametersList;
	private Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlTypeMap;
	public Long getActivityId() {
		return activityId;
	}
	public Long getStudyActivityId() {
		return studyActivityId;
	}
	public List<LanguageSpecificGlobalParameterDetails> getParametersList() {
		return parametersList;
	}
	public Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> getControlTypeMap() {
		return controlTypeMap;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}
	public void setParametersList(List<LanguageSpecificGlobalParameterDetails> parametersList) {
		this.parametersList = parametersList;
	}
	public void setControlTypeMap(Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlTypeMap) {
		this.controlTypeMap = controlTypeMap;
	}
	
	

}
