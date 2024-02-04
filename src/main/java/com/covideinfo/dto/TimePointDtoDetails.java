package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.DosingInfoData;
import com.covideinfo.model.Projects;
import com.covideinfo.model.ProjectsDetails;

@SuppressWarnings("serial")
public class TimePointDtoDetails implements Serializable {
	
	private Projects project;
	private List<ProjectsDetails> doseList;
	private List<ProjectsDetails> sampleList;
	private List<ProjectsDetails> mealsList;
	private List<ProjectsDetails> vitalsList;
	private List<ProjectsDetails> treatmentList;
	private List<ProjectsDetails> skinSensList;
	private List<ProjectsDetails> skinAdhList;
	private List<ProjectsDetails> ecgList;
	private List<ProjectsDetails> otherActList;
	private DosingInfo doseInfo;
	private Map<String, List<ProjectsDetails>> paramMap;
	private List<DosingInfoData> dosInfoDataList;
	private int noOfSubjects;
	private int noOfStandbySubjects;
	
	
	public int getNoOfSubjects() {
		return noOfSubjects;
	}
	public int getNoOfStandbySubjects() {
		return noOfStandbySubjects;
	}
	public void setNoOfSubjects(int noOfSubjects) {
		this.noOfSubjects = noOfSubjects;
	}
	public void setNoOfStandbySubjects(int noOfStandbySubjects) {
		this.noOfStandbySubjects = noOfStandbySubjects;
	}
	public List<ProjectsDetails> getDoseList() {
		return doseList;
	}
	public List<ProjectsDetails> getSampleList() {
		return sampleList;
	}
	public List<ProjectsDetails> getMealsList() {
		return mealsList;
	}
	public List<ProjectsDetails> getVitalsList() {
		return vitalsList;
	}
	public void setDoseList(List<ProjectsDetails> doseList) {
		this.doseList = doseList;
	}
	public void setSampleList(List<ProjectsDetails> sampleList) {
		this.sampleList = sampleList;
	}
	public void setMealsList(List<ProjectsDetails> mealsList) {
		this.mealsList = mealsList;
	}
	public void setVitalsList(List<ProjectsDetails> vitalsList) {
		this.vitalsList = vitalsList;
	}
	public Projects getProject() {
		return project;
	}
	public void setProject(Projects project) {
		this.project = project;
	}
	public List<ProjectsDetails> getTreatmentList() {
		return treatmentList;
	}
	public void setTreatmentList(List<ProjectsDetails> treatmentList) {
		this.treatmentList = treatmentList;
	}
	public DosingInfo getDoseInfo() {
		return doseInfo;
	}
	public List<DosingInfoData> getDosInfoDataList() {
		return dosInfoDataList;
	}
	public void setDoseInfo(DosingInfo doseInfo) {
		this.doseInfo = doseInfo;
	}
	public void setDosInfoDataList(List<DosingInfoData> dosInfoDataList) {
		this.dosInfoDataList = dosInfoDataList;
	}
	public List<ProjectsDetails> getSkinSensList() {
		return skinSensList;
	}
	public List<ProjectsDetails> getSkinAdhList() {
		return skinAdhList;
	}
	public List<ProjectsDetails> getEcgList() {
		return ecgList;
	}
	public List<ProjectsDetails> getOtherActList() {
		return otherActList;
	}
	public void setSkinSensList(List<ProjectsDetails> skinSensList) {
		this.skinSensList = skinSensList;
	}
	public void setSkinAdhList(List<ProjectsDetails> skinAdhList) {
		this.skinAdhList = skinAdhList;
	}
	public void setEcgList(List<ProjectsDetails> ecgList) {
		this.ecgList = ecgList;
	}
	public void setOtherActList(List<ProjectsDetails> otherActList) {
		this.otherActList = otherActList;
	}
	public Map<String, List<ProjectsDetails>> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, List<ProjectsDetails>> paramMap) {
		this.paramMap = paramMap;
	}
	
	
	

}
