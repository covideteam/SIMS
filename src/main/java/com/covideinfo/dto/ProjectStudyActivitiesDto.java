package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ProjectStudyActivitiesDto implements Serializable {
	
	private Long studyActiviyId;
	private String  activityName;
	private Long activityId;
	public Long getStudyActiviyId() {
		return studyActiviyId;
	}
	public String getActivityName() {
		return activityName;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setStudyActiviyId(Long studyActiviyId) {
		this.studyActiviyId = studyActiviyId;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	

}
