package com.covideinfo.dto;

public class CommentsDto {
	private Long projectId = null;
	private Long commentId = null;
	private String comment;
	private String response;
	private String commentedOn;
	private String respondedOn;
	private String commentedBy;
	private String respondedBy;
	private Long parameterId;
	private String activityTableName;
	private Boolean isTemp;
	
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getCommentedOn() {
		return commentedOn;
	}
	public void setCommentedOn(String commentedOn) {
		this.commentedOn = commentedOn;
	}
	public String getRespondedOn() {
		return respondedOn;
	}
	public void setRespondedOn(String respondedOn) {
		this.respondedOn = respondedOn;
	}
	public String getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}
	public String getRespondedBy() {
		return respondedBy;
	}
	public void setRespondedBy(String respondedBy) {
		this.respondedBy = respondedBy;
	}
	public Long getParameterId() {
		return parameterId;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	public String getActivityTableName() {
		return activityTableName;
	}
	public void setActivityTableName(String activityTableName) {
		this.activityTableName = activityTableName;
	}
	public Boolean getIsTemp() {
		return isTemp;
	}
	public void setIsTemp(Boolean isTemp) {
		this.isTemp = isTemp;
	}
}
