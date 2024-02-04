package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;

@SuppressWarnings("serial")
public class MealsDto implements Serializable {
	
	private List<MealsTimePoints> mealsList = null;
	private List<SubjectMealsTimePointsData> mtpdataList;
	private List<SubjectDoseTimePoints> sdtpList;
	public List<MealsTimePoints> getMealsList() {
		return mealsList;
	}
	public void setMealsList(List<MealsTimePoints> mealsList) {
		this.mealsList = mealsList;
	}
	public List<SubjectMealsTimePointsData> getMtpdataList() {
		return mtpdataList;
	}
	public void setMtpdataList(List<SubjectMealsTimePointsData> mtpdataList) {
		this.mtpdataList = mtpdataList;
	}
	public List<SubjectDoseTimePoints> getSdtpList() {
		return sdtpList;
	}
	public void setSdtpList(List<SubjectDoseTimePoints> sdtpList) {
		this.sdtpList = sdtpList;
	}
	
	

}
