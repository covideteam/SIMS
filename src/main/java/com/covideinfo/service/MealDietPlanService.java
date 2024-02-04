package com.covideinfo.service;

import java.util.List;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.UnitsMaster;

public interface MealDietPlanService {

	List<MealDietPlan> getMealDietPlanList();

	List<FromStaticData> getFromStaticDataForMealsTypeOnly();

	boolean saveMealDietPlan(MealDietPlan mdplan, String[] iteamValuesId, String[] quntityValuesId,
			String[] totalcalValuesId, String user, String[] unitsValuesId);

	MealDietPlan getMealDietPlanWithId(long id);

	List<MealDietPlanItem> getMealDietPlanItemWithMapId(MealDietPlan mdplan);

	MealDietPlan getMealDietPlanWithMealTitle(String mealTitle);

	List<UnitsMaster> getUnitsMasterData();
	
	

}
