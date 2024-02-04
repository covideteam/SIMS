package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class RandomizationViewDto implements Serializable {
	
	private String subjectNo;
	private List<String> period;
	private List<String> randomizationCode;
	private String createdBy;
	private long projectNo;
	private Date createdOn;
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public List<String> getPeriod() {
		return period;
	}
	public void setPeriod(List<String> period) {
		this.period = period;
	}
	public List<String> getRandomizationCode() {
		return randomizationCode;
	}
	public void setRandomizationCode(List<String> randomizationCode) {
		this.randomizationCode = randomizationCode;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public long getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(long projectNo) {
		this.projectNo = projectNo;
	}
	
	
	
	
	

}
