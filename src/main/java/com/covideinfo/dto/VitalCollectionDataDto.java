package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class VitalCollectionDataDto implements Serializable{
	private static final long serialVersionUID = -9054371806806658694L;
	private Long studyId;
	private String subject;
	private String subjectTime;
	private Long perodId;
	private Long timePointPk;
	private String sumissionTime;
	private String startTime;
	private String endTime;
	private List<String> perameterValue;
	private boolean tpSkipDeviation = false;
	private boolean timeDeviation = false;
	private String skipDeviationCode = "";
	private String timeDeviationTime = "";
	private String timeDeviationCode = "";
	private Long timeDeviationId;
	private String positionType;
	public Long getStudyId() {
		return studyId;
	}
	public String getSubject() {
		return subject;
	}
	public String getSubjectTime() {
		return subjectTime;
	}
	public Long getPerodId() {
		return perodId;
	}
	public Long getTimePointPk() {
		return timePointPk;
	}
	public String getSumissionTime() {
		return sumissionTime;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public List<String> getPerameterValue() {
		return perameterValue;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setSubjectTime(String subjectTime) {
		this.subjectTime = subjectTime;
	}
	public void setPerodId(Long perodId) {
		this.perodId = perodId;
	}
	public void setTimePointPk(Long timePointPk) {
		this.timePointPk = timePointPk;
	}
	public void setSumissionTime(String sumissionTime) {
		this.sumissionTime = sumissionTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setPerameterValue(List<String> perameterValue) {
		this.perameterValue = perameterValue;
	}
	public boolean isTpSkipDeviation() {
		return tpSkipDeviation;
	}
	public void setTpSkipDeviation(boolean tpSkipDeviation) {
		this.tpSkipDeviation = tpSkipDeviation;
	}
	public boolean isTimeDeviation() {
		return timeDeviation;
	}
	public String getTimeDeviationTime() {
		return timeDeviationTime;
	}
	public void setTimeDeviation(boolean timeDeviation) {
		this.timeDeviation = timeDeviation;
	}
	public void setTimeDeviationTime(String timeDeviationTime) {
		this.timeDeviationTime = timeDeviationTime;
	}
	public String getTimeDeviationCode() {
		return timeDeviationCode;
	}
	public void setTimeDeviationCode(String timeDeviationCode) {
		this.timeDeviationCode = timeDeviationCode;
	}
	public String getSkipDeviationCode() {
		return skipDeviationCode;
	}
	public void setSkipDeviationCode(String skipDeviationCode) {
		this.skipDeviationCode = skipDeviationCode;
	}
	public Long getTimeDeviationId() {
		return timeDeviationId;
	}
	public void setTimeDeviationId(Long timeDeviationId) {
		this.timeDeviationId = timeDeviationId;
	}
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	
	
	
	
		
	
	
}
