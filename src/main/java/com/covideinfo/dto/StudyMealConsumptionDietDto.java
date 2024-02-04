package com.covideinfo.dto;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.covideinfo.model.StudyMaster;

public class StudyMealConsumptionDietDto {
	
	private List<StudyMaster> smList;
	private List<SubjectMealTimePointsDto> subMealTpDataMap;
	private Map<Long, Set<Long>> mealDietPlanIdsMap;
	private Map<Long, List<MealDietPlanItemDto>> mealDietItemsMap;//dietPlanId, MealDietPlanItemDto
	public List<StudyMaster> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}
	public Map<Long, List<MealDietPlanItemDto>> getMealDietItemsMap() {
		return mealDietItemsMap;
	}
	public void setMealDietItemsMap(Map<Long, List<MealDietPlanItemDto>> mealDietItemsMap) {
		this.mealDietItemsMap = mealDietItemsMap;
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
	
	
	

}
