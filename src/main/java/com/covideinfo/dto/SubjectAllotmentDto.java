package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class SubjectAllotmentDto implements Serializable {
    
	private List<String> subjList;
	private int totalSubjects;
	
	public List<String> getSubjList() {
		return subjList;
	}
	public int getTotalSubjects() {
		return totalSubjects;
	}
	public void setSubjList(List<String> subjList) {
		this.subjList = subjList;
	}
	public void setTotalSubjects(int totalSubjects) {
		this.totalSubjects = totalSubjects;
	}
	
	
	

}
