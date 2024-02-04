package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DiscripancyReviewDeatails;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class CpuActivitiesReviewDataDto implements Serializable {
	
	private List<StaticFormsDataDto> sfdDtoList;
	private UserMaster user;
	private Map<Long, MealsReviewDto> mealsDataMap;
	private Map<Long, SampleCollectionDetailsDto> samplesDataMap;
	private Map<Long, VitalTimePointsDataDto> vitalDataMap;
	private Map<Long, DosingDataDto> doseDataMap;
	private  DraftReviewStage submitdrs;
	private DraftReviewStage approvedrs;
	private DraftReviewStage sendCommentsdrs;
	private Map<Long, Map<Long, Map<String, List<DiscripancyReviewDeatails>>>> drdActMap;
	private Map<Long, StaticActivityParamtersValuesDto> paramDataMap;//parmeterId, values of paratersPojo
	private String activityCode;
	public List<StaticFormsDataDto> getSfdDtoList() {
		return sfdDtoList;
	}
	public void setSfdDtoList(List<StaticFormsDataDto> sfdDtoList) {
		this.sfdDtoList = sfdDtoList;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public Map<Long, MealsReviewDto> getMealsDataMap() {
		return mealsDataMap;
	}
	public void setMealsDataMap(Map<Long, MealsReviewDto> mealsDataMap) {
		this.mealsDataMap = mealsDataMap;
	}
	public Map<Long, SampleCollectionDetailsDto> getSamplesDataMap() {
		return samplesDataMap;
	}
	public void setSamplesDataMap(Map<Long, SampleCollectionDetailsDto> samplesDataMap) {
		this.samplesDataMap = samplesDataMap;
	}
	public Map<Long, VitalTimePointsDataDto> getVitalDataMap() {
		return vitalDataMap;
	}
	public void setVitalDataMap(Map<Long, VitalTimePointsDataDto> vitalDataMap) {
		this.vitalDataMap = vitalDataMap;
	}
	public Map<Long, DosingDataDto> getDoseDataMap() {
		return doseDataMap;
	}
	public void setDoseDataMap(Map<Long, DosingDataDto> doseDataMap) {
		this.doseDataMap = doseDataMap;
	}
	public DraftReviewStage getSubmitdrs() {
		return submitdrs;
	}
	public void setSubmitdrs(DraftReviewStage submitdrs) {
		this.submitdrs = submitdrs;
	}
	public DraftReviewStage getApprovedrs() {
		return approvedrs;
	}
	public void setApprovedrs(DraftReviewStage approvedrs) {
		this.approvedrs = approvedrs;
	}
	public DraftReviewStage getSendCommentsdrs() {
		return sendCommentsdrs;
	}
	public void setSendCommentsdrs(DraftReviewStage sendCommentsdrs) {
		this.sendCommentsdrs = sendCommentsdrs;
	}
	
	public Map<Long, Map<Long, Map<String, List<DiscripancyReviewDeatails>>>> getDrdActMap() {
		return drdActMap;
	}
	public void setDrdActMap(Map<Long, Map<Long, Map<String, List<DiscripancyReviewDeatails>>>> drdActMap) {
		this.drdActMap = drdActMap;
	}
	public String getActivityCode() {
		return activityCode;
	}
	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	public Map<Long, StaticActivityParamtersValuesDto> getParamDataMap() {
		return paramDataMap;
	}
	public void setParamDataMap(Map<Long, StaticActivityParamtersValuesDto> paramDataMap) {
		this.paramDataMap = paramDataMap;
	}
	
	
	
	

}
