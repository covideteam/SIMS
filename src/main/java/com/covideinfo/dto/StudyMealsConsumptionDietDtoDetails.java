package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.covideinfo.model.StudyMaster;

public class StudyMealsConsumptionDietDtoDetails implements Serializable {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 8059448138108939445L;
	private List<StudyMaster> smList;
	private List<MealDietPlanItemDto> mealdietList;
	private List<SubjectMealTimePointsDto> subMealTpDataMap;
	private Map<Long, Set<Long>> mealDietPlanIdsMap;//MealId, List of dietplanitemIds
	private List<Long> subjectMealIds;
	public List<StudyMaster> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}
	public List<MealDietPlanItemDto> getMealdietList() {
		return mealdietList;
	}
	public void setMealdietList(List<MealDietPlanItemDto> mealdietList) {
		this.mealdietList = mealdietList;
	}
	public List<SubjectMealTimePointsDto> getSubMealTpDataMap() {
		return subMealTpDataMap;
	}
	public void setSubMealTpDataMap(List<SubjectMealTimePointsDto> subMealTpDataMap) {
		this.subMealTpDataMap = subMealTpDataMap;
	}
	public Map<Long, Set<Long>> getMealDietPlanIdsMap() {
		return mealDietPlanIdsMap;
	}
	public void setMealDietPlanIdsMap(Map<Long, Set<Long>> mealDietPlanIdsMap) {
		this.mealDietPlanIdsMap = mealDietPlanIdsMap;
	}
	public List<Long> getSubjectMealIds() {
		return subjectMealIds;
	}
	public void setSubjectMealIds(List<Long> subjectMealIds) {
		this.subjectMealIds = subjectMealIds;
	}
	

}
