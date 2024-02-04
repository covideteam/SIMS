package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PeriodMasterDto implements Serializable {
	
	private Long id;
	private String periodName;
	private int periodNo;
	private Long studyId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}

	
	
}
