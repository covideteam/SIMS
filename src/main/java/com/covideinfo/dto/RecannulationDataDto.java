package com.covideinfo.dto;

import java.io.Serializable;
import java.util.Date;

public class RecannulationDataDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2603599208271421486L;
	private Long studyId;
	private Long periodId;
	private Long subjectId;
	private Long sampleId;
	private String reason;
	private String doneBy;
	private Date recannulationDate;
	private boolean recannula;
	private boolean cannulaRemoved;
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public Long getPeriodId() {
		return periodId;
	}
	public void setPeriodId(Long periodId) {
		this.periodId = periodId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public Long getSampleId() {
		return sampleId;
	}
	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getDoneBy() {
		return doneBy;
	}
	public void setDoneBy(String doneBy) {
		this.doneBy = doneBy;
	}
	public Date getRecannulationDate() {
		return recannulationDate;
	}
	public void setRecannulationDate(Date recannulationDate) {
		this.recannulationDate = recannulationDate;
	}
	public boolean isRecannula() {
		return recannula;
	}
	public void setRecannula(boolean recannula) {
		this.recannula = recannula;
	}
	public boolean isCannulaRemoved() {
		return cannulaRemoved;
	}
	public void setCannulaRemoved(boolean cannulaRemoved) {
		this.cannulaRemoved = cannulaRemoved;
	}
	
	
	
	

}
