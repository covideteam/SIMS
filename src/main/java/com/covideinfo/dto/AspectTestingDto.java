package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AspectTestingDto implements Serializable {
	
	private String userName;
	private String projectName;
	public String getUserName() {
		return userName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	

}
