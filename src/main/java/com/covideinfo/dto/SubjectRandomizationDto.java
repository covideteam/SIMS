package com.covideinfo.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SubjectRandomizationDto implements Serializable, Comparable<SubjectRandomizationDto> {
	
	private Long id;
	private String randomizationCode;
	private String subjectNo;
	private String periodName;
	private int periodNo;
	private Long periodId;
	private int subjNo;
	private Long treatmentId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRandomizationCode() {
		return randomizationCode;
	}
	public void setRandomizationCode(String randomizationCode) {
		this.randomizationCode = randomizationCode;
	}
	public String getSubjectNo() {
		return subjectNo;
	}
	public void setSubjectNo(String subjectNo) {
		this.subjectNo = subjectNo;
	}
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	@Override
	public int compareTo(SubjectRandomizationDto o) {
		return Integer.compare(this.subjNo, o.subjNo);
	}
	public int getSubjNo() {
		return subjNo;
	}
	public void setSubjNo(int subjNo) {
		this.subjNo = subjNo;
	}
	public Long getTreatmentId() {
		return treatmentId;
	}
	public void setTreatmentId(Long treatmentId) {
		this.treatmentId = treatmentId;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
	}
	
	

}
