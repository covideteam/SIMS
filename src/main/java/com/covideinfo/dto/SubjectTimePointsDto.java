package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class SubjectTimePointsDto implements Serializable {
	
	private List<SubjectMealsTimePointsData> mealsDtaList;
	private List<SubjectSampleCollectionTimePointsData> samDataList;
	private List<SubjectVitalTimePointsData> svDataList;
	private UserMaster user;
	private DeviationMessage dm;
	private StatusMaster newStatus;
	private GlobalActivity mealActvity;
	private GlobalActivity sampleActivity;
	private GlobalActivity vitalCollectionActivity;
	
	public List<SubjectMealsTimePointsData> getMealsDtaList() {
		return mealsDtaList;
	}
	public void setMealsDtaList(List<SubjectMealsTimePointsData> mealsDtaList) {
		this.mealsDtaList = mealsDtaList;
	}
	public List<SubjectSampleCollectionTimePointsData> getSamDataList() {
		return samDataList;
	}
	public void setSamDataList(List<SubjectSampleCollectionTimePointsData> samDataList) {
		this.samDataList = samDataList;
	}
	public List<SubjectVitalTimePointsData> getSvDataList() {
		return svDataList;
	}
	public void setSvDataList(List<SubjectVitalTimePointsData> svDataList) {
		this.svDataList = svDataList;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}
	public DeviationMessage getDm() {
		return dm;
	}
	public void setDm(DeviationMessage dm) {
		this.dm = dm;
	}
	public StatusMaster getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(StatusMaster newStatus) {
		this.newStatus = newStatus;
	}
	public GlobalActivity getMealActvity() {
		return mealActvity;
	}
	public void setMealActvity(GlobalActivity mealActvity) {
		this.mealActvity = mealActvity;
	}
	public GlobalActivity getSampleActivity() {
		return sampleActivity;
	}
	public void setSampleActivity(GlobalActivity sampleActivity) {
		this.sampleActivity = sampleActivity;
	}
	public GlobalActivity getVitalCollectionActivity() {
		return vitalCollectionActivity;
	}
	public void setVitalCollectionActivity(GlobalActivity vitalCollectionActivity) {
		this.vitalCollectionActivity = vitalCollectionActivity;
	}
	
	

}
