package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.Projects;

@SuppressWarnings("serial")
public class DosingInfoDetialsDto implements Serializable {
	
	private Projects project;
	private Map<String, Map<String, List<TimePointsDto>>> timePointsMap;
	private DosingInfo doseInfo;
	private Map<String, Map<Integer, List<Long>>> parametersMap;
	private int noOfSubjects;
	private int noOfStandbySubjects;
	private boolean trwDoseFlag = false;
	private boolean trwSampleFlag = false;
	private boolean trwMealsFlag = false;
	private boolean trwVitalFlag = false;
	private boolean trwSkinFlag = false;
	private boolean trwSkinAdhFlag = false;
	private boolean trwEcgFlag = false;
	private boolean trwOtherFlag = false;
	
	public Projects getProject() {
		return project;
	}
	public Map<String, Map<String, List<TimePointsDto>>> getTimePointsMap() {
		return timePointsMap;
	}
	public DosingInfo getDoseInfo() {
		return doseInfo;
	}
	public void setProject(Projects project) {
		this.project = project;
	}
	public void setTimePointsMap(Map<String, Map<String, List<TimePointsDto>>> timePointsMap) {
		this.timePointsMap = timePointsMap;
	}
	public void setDoseInfo(DosingInfo doseInfo) {
		this.doseInfo = doseInfo;
	}
	public Map<String, Map<Integer, List<Long>>> getParametersMap() {
		return parametersMap;
	}
	public void setParametersMap(Map<String, Map<Integer, List<Long>>> parametersMap) {
		this.parametersMap = parametersMap;
	}
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
	public boolean isTrwDoseFlag() {
		return trwDoseFlag;
	}
	public boolean isTrwSampleFlag() {
		return trwSampleFlag;
	}
	public boolean isTrwMealsFlag() {
		return trwMealsFlag;
	}
	public boolean isTrwVitalFlag() {
		return trwVitalFlag;
	}
	public boolean isTrwSkinFlag() {
		return trwSkinFlag;
	}
	public boolean isTrwSkinAdhFlag() {
		return trwSkinAdhFlag;
	}
	public boolean isTrwEcgFlag() {
		return trwEcgFlag;
	}
	public boolean isTrwOtherFlag() {
		return trwOtherFlag;
	}
	public void setTrwDoseFlag(boolean trwDoseFlag) {
		this.trwDoseFlag = trwDoseFlag;
	}
	public void setTrwSampleFlag(boolean trwSampleFlag) {
		this.trwSampleFlag = trwSampleFlag;
	}
	public void setTrwMealsFlag(boolean trwMealsFlag) {
		this.trwMealsFlag = trwMealsFlag;
	}
	public void setTrwVitalFlag(boolean trwVitalFlag) {
		this.trwVitalFlag = trwVitalFlag;
	}
	public void setTrwSkinFlag(boolean trwSkinFlag) {
		this.trwSkinFlag = trwSkinFlag;
	}
	public void setTrwSkinAdhFlag(boolean trwSkinAdhFlag) {
		this.trwSkinAdhFlag = trwSkinAdhFlag;
	}
	public void setTrwEcgFlag(boolean trwEcgFlag) {
		this.trwEcgFlag = trwEcgFlag;
	}
	public void setTrwOtherFlag(boolean trwOtherFlag) {
		this.trwOtherFlag = trwOtherFlag;
	}
	
	
	
	
	
	
	


}
