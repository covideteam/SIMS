package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserMaster;

@SuppressWarnings("serial")
public class MealMenuReportsDetailsDto implements Serializable {
	
	private List<StudyMasterDto> smList;
	private List<StudyPeriodsDto> spmList;
	private List<StudyLevelConfiguredMealsDietDetailsDto> slcmdList;
	private List<MealDietPlanItemDto> mealDietItemsList = null;
	private String studyName;
	private UserMaster user;
	public List<StudyMasterDto> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMasterDto> smList) {
		this.smList = smList;
	}
	public List<StudyPeriodsDto> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodsDto> spmList) {
		this.spmList = spmList;
	}
	public List<StudyLevelConfiguredMealsDietDetailsDto> getSlcmdList() {
		return slcmdList;
	}
	public void setSlcmdList(List<StudyLevelConfiguredMealsDietDetailsDto> slcmdList) {
		this.slcmdList = slcmdList;
	}
	public List<MealDietPlanItemDto> getMealDietItemsList() {
		return mealDietItemsList;
	}
	public void setMealDietItemsList(List<MealDietPlanItemDto> mealDietItemsList) {
		this.mealDietItemsList = mealDietItemsList;
	}
	public String getStudyName() {
		return studyName;
	}
	public void setStudyName(String studyName) {
		this.studyName = studyName;
	}
	public UserMaster getUser() {
		return user;
	}
	public void setUser(UserMaster user) {
		this.user = user;
	}

	
	

}
