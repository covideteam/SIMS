package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.UnitsMaster;

public interface MealDietPlanDao {

	List<MealDietPlan> getMealDietPlanList();

	List<FromStaticData> getFromStaticDataForMealsTypeOnly();

	boolean saveMealDietPlanData(MealDietPlan mdplan, List<MealDietPlanItem> milist);

	MealDietPlan getMealDietPlanWithId(long id);

	List<MealDietPlanItem> getMealDietPlanItemWithMapId(MealDietPlan mdplan);

	MealDietPlan getMealDietPlanWithMealTitle(String mealTitle);

	List<UnitsMaster> getUnitsMasterData();

	UnitsMaster getUnitsMasterWithUnitcode(String string);

}
