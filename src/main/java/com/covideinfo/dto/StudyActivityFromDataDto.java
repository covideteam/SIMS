package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class StudyActivityFromDataDto implements Serializable {
	
	private Long studyId;
    private Long activityId;
    private Long volunteerId;
    private Long subjectId;
    private Long studyActivityId;
    private String phase;
	private List<String> parameterVals; //@@paramerterId@@value
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getVolunteerId() {
		return volunteerId;
	}
	public void setVolunteerId(Long volunteerId) {
		this.volunteerId = volunteerId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getStudyActivityId() {
		return studyActivityId;
	}
	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public List<String> getParameterVals() {
		return parameterVals;
	}
	public void setParameterVals(List<String> parameterVals) {
		this.parameterVals = parameterVals;
	}
	
	
	
	

}
