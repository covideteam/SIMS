package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DiscripancyReviewDeatails;
import com.covideinfo.model.DraftReviewStage;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.SubjectDoseTimePointParametersData;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalParametersData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class CpuActivitiesDetailsDto implements Serializable {
	
	private List<SubjectMealsTimePointsData> mealsDataList;
	private List<SubjectSampleCollectionTimePointsData> samplesDtaList;
	private List<SubjectVitalTimePointsData> vitalsDtaList;
	private List<SubjectVitalParametersData> vitalParmsList;
	private List<SubjectDoseTimePoints> doseDataList;
	private List<SubjectDoseTimePointParametersData> doseParamList;
	private UserMaster user;
	private List<LanguageSpecificGlobalParameterDetails> lspgpdList;
	private Map<Long, List<LanguageSpecificGroupDetails>> lspgMap;
	private DraftReviewStage submitdrs;
	private DraftReviewStage approvedrs;
	private DraftReviewStage sendCommentsdrs;
	private List<DiscripancyReviewDeatails> drdList;
	
	public List<SubjectMealsTimePointsData> getMealsDataList() {
		return mealsDataList;
	}
	public void setMealsDataList(List<SubjectMealsTimePointsData> mealsDataList) {
		this.mealsDataList = mealsDataList;
	}
	public List<SubjectSampleCollectionTimePointsData> getSamplesDtaList() {
		return samplesDtaList;
	}
	public void setSamplesDtaList(List<SubjectSampleCollectionTimePointsData> samplesDtaList) {
		this.samplesDtaList = samplesDtaList;
	}
	public List<SubjectVitalTimePointsData> getVitalsDtaList() {
		return vitalsDtaList;
	}
	public void setVitalsDtaList(List<SubjectVitalTimePointsData> vitalsDtaList) {
		this.vitalsDtaList = vitalsDtaList;
	}
	public List<SubjectVitalParametersData> getVitalParmsList() {
		return vitalParmsList;
	}
	public void setVitalParmsList(List<SubjectVitalParametersData> vitalParmsList) {
		this.vitalParmsList = vitalParmsList;
	}
	public List<SubjectDoseTimePoints> getDoseDataList() {
		return doseDataList;
	}
	public void setDoseDataList(List<SubjectDoseTimePoints> doseDataList) {
		this.doseDataList = doseDataList;
	}
	public List<SubjectDoseTimePointParametersData> getDoseParamList() {
		return doseParamList;
	}
	public void setDoseParamList(List<SubjectDoseTimePointParametersData> doseParamList) {
		this.doseParamList = doseParamList;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public List<LanguageSpecificGlobalParameterDetails> getLspgpdList() {
		return lspgpdList;
	}
	public void setLspgpdList(List<LanguageSpecificGlobalParameterDetails> lspgpdList) {
		this.lspgpdList = lspgpdList;
	}
	public Map<Long, List<LanguageSpecificGroupDetails>> getLspgMap() {
		return lspgMap;
	}
	public void setLspgMap(Map<Long, List<LanguageSpecificGroupDetails>> lspgMap) {
		this.lspgMap = lspgMap;
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
	public List<DiscripancyReviewDeatails> getDrdList() {
		return drdList;
	}
	public void setDrdList(List<DiscripancyReviewDeatails> drdList) {
		this.drdList = drdList;
	}
	
	
	
	
	

}
