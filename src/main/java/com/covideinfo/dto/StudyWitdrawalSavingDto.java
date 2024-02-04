package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class StudyWitdrawalSavingDto implements Serializable {
	
	private List<Long> parameterIds;
	private List<String> parameterValueIds;
	private String type;
	private Long projectId;
	private Long activityId;
	private String comments;
	private Long volunteer;
	private String withdrawLevel;
	
	public List<Long> getParameterIds() {
		return parameterIds;
	}
	public void setParameterIds(List<Long> parameterIds) {
		this.parameterIds = parameterIds;
	}
	public List<String> getParameterValueIds() {
		return parameterValueIds;
	}
	public void setParameterValueIds(List<String> parameterValueIds) {
		this.parameterValueIds = parameterValueIds;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Long getVolunteer() {
		return volunteer;
	}
	public void setVolunteer(Long volunteer) {
		this.volunteer = volunteer;
	}
	public String getWithdrawLevel() {
		return withdrawLevel;
	}
	public void setWithdrawLevel(String withdrawLevel) {
		this.withdrawLevel = withdrawLevel;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
