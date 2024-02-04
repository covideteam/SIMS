package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

public class DoseDataDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3573414414235494498L;
	private Long studyId;
	private Long activityId;
	private Long studyActivityId;
	private Long perodId;
	private String sachetBarcode;
	private String sachetBarcodeScanTime;
	private String subjectBarcode;
	private String subjectBarcodeScanTime;
	private String collectionTime;
	private Long devationMsgId;
	private boolean replaceStatus;
	private String replaceSubject;
	private boolean timeDeviation;
	private String  timeDeviationTime;
	private boolean criteriaDeviation;
	private String  criteriaDeviationTime;
	private Long timeDeviationCodeId;
	private Long criteriaDeviationTimeCodeId;
	
	private Long timePointPk;
	private List<Long> perameterIds;
	private List<String> perameterValue;
	
	private String fastCriteraComments = "";
	private String feadCriteraComments = "";
	
	
	public String getFastCriteraComments() {
		return fastCriteraComments;
	}
	public void setFastCriteraComments(String fastCriteraComments) {
		this.fastCriteraComments = fastCriteraComments;
	}
	public String getFeadCriteraComments() {
		return feadCriteraComments;
	}
	public void setFeadCriteraComments(String feadCriteraComments) {
		this.feadCriteraComments = feadCriteraComments;
	}
	public Long getActivityId() {
		return activityId;
	}
	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}
	public Long getStudyActivityId() {
		return studyActivityId;
	}
	public void setStudyActivityId(Long studyActivityId) {
		this.studyActivityId = studyActivityId;
	}
	public Long getPerodId() {
		return perodId;
	}
	public void setPerodId(Long perodId) {
		this.perodId = perodId;
	}
	public Long getTimePointPk() {
		return timePointPk;
	}
	public void setTimePointPk(Long timePointPk) {
		this.timePointPk = timePointPk;
	}
	public List<Long> getPerameterIds() {
		return perameterIds;
	}
	public void setPerameterIds(List<Long> perameterIds) {
		this.perameterIds = perameterIds;
	}
	public List<String> getPerameterValue() {
		return perameterValue;
	}
	public void setPerameterValue(List<String> perameterValue) {
		this.perameterValue = perameterValue;
	}
	public boolean isReplaceStatus() {
		return replaceStatus;
	}
	public void setReplaceStatus(boolean replaceStatus) {
		this.replaceStatus = replaceStatus;
	}
	public String getReplaceSubject() {
		return replaceSubject;
	}
	public void setReplaceSubject(String replaceSubject) {
		this.replaceSubject = replaceSubject;
	}
	
	public Long getDevationMsgId() {
		return devationMsgId;
	}
	public void setDevationMsgId(Long devationMsgId) {
		this.devationMsgId = devationMsgId;
	}
	public Long getStudyId() {
		return studyId;
	}
	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}
	public String getSachetBarcode() {
		return sachetBarcode;
	}
	public void setSachetBarcode(String sachetBarcode) {
		this.sachetBarcode = sachetBarcode;
	}
	public String getSachetBarcodeScanTime() {
		return sachetBarcodeScanTime;
	}
	public void setSachetBarcodeScanTime(String sachetBarcodeScanTime) {
		this.sachetBarcodeScanTime = sachetBarcodeScanTime;
	}
	public String getSubjectBarcode() {
		return subjectBarcode;
	}
	public void setSubjectBarcode(String subjectBarcode) {
		this.subjectBarcode = subjectBarcode;
	}
	public String getSubjectBarcodeScanTime() {
		return subjectBarcodeScanTime;
	}
	public void setSubjectBarcodeScanTime(String subjectBarcodeScanTime) {
		this.subjectBarcodeScanTime = subjectBarcodeScanTime;
	}
	public String getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	public boolean isTimeDeviation() {
		return timeDeviation;
	}
	public void setTimeDeviation(boolean timeDeviation) {
		this.timeDeviation = timeDeviation;
	}
	public String getTimeDeviationTime() {
		return timeDeviationTime;
	}
	public void setTimeDeviationTime(String timeDeviationTime) {
		this.timeDeviationTime = timeDeviationTime;
	}
	public boolean isCriteriaDeviation() {
		return criteriaDeviation;
	}
	public void setCriteriaDeviation(boolean criteriaDeviation) {
		this.criteriaDeviation = criteriaDeviation;
	}
	public String getCriteriaDeviationTime() {
		return criteriaDeviationTime;
	}
	public void setCriteriaDeviationTime(String criteriaDeviationTime) {
		this.criteriaDeviationTime = criteriaDeviationTime;
	}
	public Long getTimeDeviationCodeId() {
		return timeDeviationCodeId;
	}
	public void setTimeDeviationCodeId(Long timeDeviationCodeId) {
		this.timeDeviationCodeId = timeDeviationCodeId;
	}
	public Long getCriteriaDeviationTimeCodeId() {
		return criteriaDeviationTimeCodeId;
	}
	public void setCriteriaDeviationTimeCodeId(Long criteriaDeviationTimeCodeId) {
		this.criteriaDeviationTimeCodeId = criteriaDeviationTimeCodeId;
	}
	
}
