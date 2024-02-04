package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;

import com.covideinfo.dto.MealsTpsDto;
import com.covideinfo.dto.StudyPeriodsDto;
import com.covideinfo.dto.SubjectsDto;

public class AllowMealsRecordsDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5945241165634191758L;
	private List<StudyPeriodsDto> spmList;
	private List<MealsTpsDto> mealsList;
	private List<SubjectsDto> subjectList;
	private List<AllowStudySubjectMealsDto> allowMealsList;
	public List<StudyPeriodsDto> getSpmList() {
		return spmList;
	}
	public void setSpmList(List<StudyPeriodsDto> spmList) {
		this.spmList = spmList;
	}
	public List<MealsTpsDto> getMealsList() {
		return mealsList;
	}
	public void setMealsList(List<MealsTpsDto> mealsList) {
		this.mealsList = mealsList;
	}
	public List<SubjectsDto> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<SubjectsDto> subjectList) {
		this.subjectList = subjectList;
	}
	public List<AllowStudySubjectMealsDto> getAllowMealsList() {
		return allowMealsList;
	}
	public void setAllowMealsList(List<AllowStudySubjectMealsDto> allowMealsList) {
		this.allowMealsList = allowMealsList;
	}
	
	
	

}
