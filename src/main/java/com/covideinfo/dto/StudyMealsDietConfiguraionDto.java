package com.covideinfo.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.covideinfo.model.StudyMaster;

public class StudyMealsDietConfiguraionDto implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6478532224559948151L;
	private List<StudyMaster> smList;
	private List<StudyPeriodsDto> studyPeriods;
	private List<MealsInfoDetailsDto> mealDtoList;
	private Map<String, List<MealDietPlanDto>> mealPlanMap;
	private Map<Long, Map<String, Long>> mealTPDietMap;//PeriodId, MealTimePointId, MealDietId
	public List<StudyMaster> getSmList() {
		return smList;
	}
	public void setSmList(List<StudyMaster> smList) {
		this.smList = smList;
	}
	public List<StudyPeriodsDto> getStudyPeriods() {
		return studyPeriods;
	}
	public void setStudyPeriods(List<StudyPeriodsDto> studyPeriods) {
		this.studyPeriods = studyPeriods;
	}
	public List<MealsInfoDetailsDto> getMealDtoList() {
		return mealDtoList;
	}
	public void setMealDtoList(List<MealsInfoDetailsDto> mealDtoList) {
		this.mealDtoList = mealDtoList;
	}
	public Map<String, List<MealDietPlanDto>> getMealPlanMap() {
		return mealPlanMap;
	}
	public void setMealPlanMap(Map<String, List<MealDietPlanDto>> mealPlanMap) {
		this.mealPlanMap = mealPlanMap;
	}
	public Map<Long, Map<String, Long>> getMealTPDietMap() {
		return mealTPDietMap;
	}
	public void setMealTPDietMap(Map<Long, Map<String, Long>> mealTPDietMap) {
		this.mealTPDietMap = mealTPDietMap;
	}
	
	
	
	
	
	

}
