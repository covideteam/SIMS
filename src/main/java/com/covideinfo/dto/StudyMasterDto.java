package com.covideinfo.dto;

import java.io.Serializable;

public class StudyMasterDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8140128915923992738L;
	private Long id;
	private String projectNo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	

}
