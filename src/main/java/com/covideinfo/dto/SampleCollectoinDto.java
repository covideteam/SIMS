package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class SampleCollectoinDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1870801474632467096L;
	private Long studyId;
	private boolean skipDeviation;
	private boolean timeDeviation;
	private String timeDeviationCode ="";
	private String skipDeviationCode ="";
	private String deviationTime ="";
	private List<Long> skipedTpIds;
	private String subject;
	private String subjectTime;
	private String vacutainer;
	private String vacutainerTime;
	private String collectionTime;
	private String deviationStatusMsg;
	private String runningTimeWithSeconds;
	private String timeDeviationTime;
	private Long sampleDevCodeId;
	private Long sampleDevCodeCommentsId;
	private String modeOfCollection;
	private Long  deviationCommentsId;
	private String sampleReason;
	
	
	public Long getStudyId() {
		return studyId;
	}
	public boolean isSkipDeviation() {
		return skipDeviation;
	}
	public boolean isTimeDeviation() {
		return timeDeviation;
	}
	public String getDeviationTime() {
		return deviationTime;
	}
	public List<Long> getSkipedTpIds() {
		return skipedTpIds;
	}
	public String getSubject() {
		return subject;
	}
	public String getSubjectTime() {
		return subjectTime;
	}
	public String getVacutainer() {
		return vacutainer;
	}
	public String getVacutainerTime() {
		return vacutainerTime;
	}
	public String getCollectionTime() {
		return collectionTime;
	}
	public String getDeviationStatusMsg() {
		return deviationStatusMsg;
	}
	public String getRunningTimeWithSeconds() {
		return runningTimeWithSeconds;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public void setSkipDeviation(boolean skipDeviation) {
		this.skipDeviation = skipDeviation;
	}
	public void setTimeDeviation(boolean timeDeviation) {
		this.timeDeviation = timeDeviation;
	}
	public void setDeviationTime(String deviationTime) {
		this.deviationTime = deviationTime;
	}
	public void setSkipedTpIds(List<Long> skipedTpIds) {
		this.skipedTpIds = skipedTpIds;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getTimeDeviationCode() {
		return timeDeviationCode;
	}
	public void setTimeDeviationCode(String timeDeviationCode) {
		this.timeDeviationCode = timeDeviationCode;
	}
	public void setSubjectTime(String subjectTime) {
		this.subjectTime = subjectTime;
	}
	public void setVacutainer(String vacutainer) {
		this.vacutainer = vacutainer;
	}
	public void setVacutainerTime(String vacutainerTime) {
		this.vacutainerTime = vacutainerTime;
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	public void setDeviationStatusMsg(String deviationStatusMsg) {
		this.deviationStatusMsg = deviationStatusMsg;
	}
	public void setRunningTimeWithSeconds(String runningTimeWithSeconds) {
		this.runningTimeWithSeconds = runningTimeWithSeconds;
	}
	public String getSkipDeviationCode() {
		return skipDeviationCode;
	}
	public void setSkipDeviationCode(String skipDeviationCode) {
		this.skipDeviationCode = skipDeviationCode;
	}
	public String getTimeDeviationTime() {
		return timeDeviationTime;
	}
	public String getModeOfCollection() {
		return modeOfCollection;
	}
	public void setModeOfCollection(String modeOfCollection) {
		this.modeOfCollection = modeOfCollection;
	}
	public Long getDeviationCommentsId() {
		return deviationCommentsId;
	}
	public void setDeviationCommentsId(Long deviationCommentsId) {
		this.deviationCommentsId = deviationCommentsId;
	}
	public String getSampleReason() {
		return sampleReason;
	}
	public void setSampleReason(String sampleReason) {
		this.sampleReason = sampleReason;
	}
	public Long getSampleDevCodeId() {
		return sampleDevCodeId;
	}
	public void setSampleDevCodeId(Long sampleDevCodeId) {
		this.sampleDevCodeId = sampleDevCodeId;
	}
	public void setTimeDeviationTime(String timeDeviationTime) {
		this.timeDeviationTime = timeDeviationTime;
	}
	public Long getSampleDevCodeCommentsId() {
		return sampleDevCodeCommentsId;
	}
	public void setSampleDevCodeCommentsId(Long sampleDevCodeCommentsId) {
		this.sampleDevCodeCommentsId = sampleDevCodeCommentsId;
	}
	
	
}
