package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class DosingInfoSavingDetailsDto implements Serializable{
	
	private Long projectId;
	private String projectNo;
	private String dosingDate;
	private int dsdifferenceBetweenSubjects;
	private String dosingTime;
	private int noOfStations;
	private List<String> samplesTpVals;
	private List<String> doseTpVals;
	private List<String> mealsTpVal;
	private List<String> vitalsTpVal;
	private List<String> skinSenTpVals;
	private List<String> skinAdhTpVals;
	private List<String> ecgTpVals;
	private List<String> ohterTpVals;
	private List<String> treatmentNamesList;
	public Long getProjectId() {
		return projectId;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public String getDosingDate() {
		return dosingDate;
	}
	public int getDsdifferenceBetweenSubjects() {
		return dsdifferenceBetweenSubjects;
	}
	public String getDosingTime() {
		return dosingTime;
	}
	public int getNoOfStations() {
		return noOfStations;
	}
	public List<String> getSamplesTpVals() {
		return samplesTpVals;
	}
	public List<String> getDoseTpVals() {
		return doseTpVals;
	}
	public List<String> getMealsTpVal() {
		return mealsTpVal;
	}
	public List<String> getVitalsTpVal() {
		return vitalsTpVal;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public void setDosingDate(String dosingDate) {
		this.dosingDate = dosingDate;
	}
	public void setDsdifferenceBetweenSubjects(int dsdifferenceBetweenSubjects) {
		this.dsdifferenceBetweenSubjects = dsdifferenceBetweenSubjects;
	}
	public void setDosingTime(String dosingTime) {
		this.dosingTime = dosingTime;
	}
	public void setNoOfStations(int noOfStations) {
		this.noOfStations = noOfStations;
	}
	public void setSamplesTpVals(List<String> samplesTpVals) {
		this.samplesTpVals = samplesTpVals;
	}
	public void setDoseTpVals(List<String> doseTpVals) {
		this.doseTpVals = doseTpVals;
	}
	public void setMealsTpVal(List<String> mealsTpVal) {
		this.mealsTpVal = mealsTpVal;
	}
	public void setVitalsTpVal(List<String> vitalsTpVal) {
		this.vitalsTpVal = vitalsTpVal;
	}
	public List<String> getSkinSenTpVals() {
		return skinSenTpVals;
	}
	public List<String> getSkinAdhTpVals() {
		return skinAdhTpVals;
	}
	public List<String> getEcgTpVals() {
		return ecgTpVals;
	}
	public List<String> getOhterTpVals() {
		return ohterTpVals;
	}
	public void setSkinSenTpVals(List<String> skinSenTpVals) {
		this.skinSenTpVals = skinSenTpVals;
	}
	public void setSkinAdhTpVals(List<String> skinAdhTpVals) {
		this.skinAdhTpVals = skinAdhTpVals;
	}
	public void setEcgTpVals(List<String> ecgTpVals) {
		this.ecgTpVals = ecgTpVals;
	}
	public void setOhterTpVals(List<String> ohterTpVals) {
		this.ohterTpVals = ohterTpVals;
	}
	public List<String> getTreatmentNamesList() {
		return treatmentNamesList;
	}
	public void setTreatmentNamesList(List<String> treatmentNamesList) {
		this.treatmentNamesList = treatmentNamesList;
	}
	
	

}
