package com.covideinfo.dto;

import java.io.Serializable;

public class StudyDesignFromDto implements Serializable {
	
	private static final long serialVersionUID = 8966161326419167565L;
	private String studyId;
	private String subjectId;
	private String activityName;
	public String getStudyId() {
		return studyId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	
	

}
