
package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubjectsDto implements Serializable {
	
	private Long subjectId;
	private String subjectNo;
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	
}
