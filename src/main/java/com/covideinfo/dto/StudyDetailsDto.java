package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
	
@SuppressWarnings("serial")
public class StudyDetailsDto implements Serializable {
	
	private Long studyId;
	private String studyName;
	private List<ProjectStudyActivitiesDto> pwsactList;
	public Long getStudyId() {
		return studyId;
	}
	public String getStudyName() {
		return studyName;
	}
	public List<ProjectStudyActivitiesDto> getPwsactList() {
		return pwsactList;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public void setPwsactList(List<ProjectStudyActivitiesDto> pwsactList) {
		this.pwsactList = pwsactList;
	}
	
	
	

}
