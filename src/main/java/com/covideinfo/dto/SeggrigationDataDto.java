package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class SeggrigationDataDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8799918535609871762L;
	private Long studyId;
	private Long periodId;
	private String cleanArea;
	private String dataLogger;
	private String aliquot;
	private String allSubjectb;
	private String subject;
	private List<String> scanedVials;
	private List<String> scanedVialsTimes;
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
	public String getCleanArea() {
		return cleanArea;
	}
	public void setCleanArea(String cleanArea) {
		this.cleanArea = cleanArea;
	}
	public String getDataLogger() {
		return dataLogger;
	}
	public void setDataLogger(String dataLogger) {
		this.dataLogger = dataLogger;
	}
	public String getAliquot() {
		return aliquot;
	}
	public void setAliquot(String aliquot) {
		this.aliquot = aliquot;
	}
	public String getAllSubjectb() {
		return allSubjectb;
	}
	public void setAllSubjectb(String allSubjectb) {
		this.allSubjectb = allSubjectb;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public List<String> getScanedVials() {
		return scanedVials;
	}
	public void setScanedVials(List<String> scanedVials) {
		this.scanedVials = scanedVials;
	}
	public List<String> getScanedVialsTimes() {
		return scanedVialsTimes;
	}
	public void setScanedVialsTimes(List<String> scanedVialsTimes) {
		this.scanedVialsTimes = scanedVialsTimes;
	}
	@Override
	public String toString() {
		return "SeggrigationDataDto [studyId=" + studyId + ", periodId=" + periodId + ", cleanArea=" + cleanArea
				+ ", dataLogger=" + dataLogger + ", aliquot=" + aliquot + ", allSubjectb=" + allSubjectb + ", subject="
				+ subject + ", scanedVials=" + scanedVials + ", scanedVialsTimes=" + scanedVialsTimes + "]";
	}
	
	
}
