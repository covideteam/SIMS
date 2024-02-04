package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StudyActivityDataDto implements Serializable {
	
	private Long satudyActivityDataId;
	private Long studyActivityId;
	private Long volunteerId;
	public Long getSatudyActivityDataId() {
		return satudyActivityDataId;
	}
	public void setSatudyActivityDataId(Long satudyActivityDataId) {
		this.satudyActivityDataId = satudyActivityDataId;
	}
	public Long getStudyActivityId() {
		return studyActivityId;
	}
	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}
	public Long getVolunteerId() {
		return volunteerId;
	}
	public void setVolunteerId(Long volunteerId) {
		this.volunteerId = volunteerId;
	}
	
	

}
