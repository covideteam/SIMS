package com.covideinfo.model.dummy;

public class ProjectDetailsKeyVal {

	private long projectId;
	private long projectsDetailsId;
	private String comments;
	private String actionName;//SUBMIT,APPROVAL,SENDCOMMENTS,APPROVAL,SENDCOMMENTS
	private long roleid;
	
	
	public long getProjectsDetailsId() {
		return projectsDetailsId;
	}
	public void setProjectsDetailsId(long projectsDetailsId) {
		this.projectsDetailsId = projectsDetailsId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public long getRoleid() {
		return roleid;
	}
	public void setRoleid(long roleid) {
		this.roleid = roleid;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	
	
	
	
	
}
