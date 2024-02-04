package com.covideinfo.dto;

import java.io.Serializable;

public class SubjectSamplesDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4750175337018286137L;
	private Long id;
	private Long sampleTpId;
	private String sign;
	private String timePoint;
	private Long subjectId;
	private String subjectNo;
	private Long periodId;
	private String periodName;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSampleTpId() {
		return sampleTpId;
	}
	public void setSampleTpId(Long sampleTpId) {
		this.sampleTpId = sampleTpId;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimePoint() {
		return timePoint;
	}
	public void setTimePoint(String timePoint) {
		this.timePoint = timePoint;
	}
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
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	
	
	
	

}
