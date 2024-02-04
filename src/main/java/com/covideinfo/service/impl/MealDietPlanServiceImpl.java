package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.MealDietPlanDao;
import com.covideinfo.general.DobConversion;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.UnitsMaster;
import com.covideinfo.service.MealDietPlanService;

@Service("mealDietPlanService")
public class MealDietPlanServiceImpl implements MealDietPlanService {

	@Autowired
	MealDietPlanDao mealDietPlanDao;
	
	@Override
	public List<MealDietPlan> getMealDietPlanList() {
		return mealDietPlanDao.getMealDietPlanList();
	}

	@Override
	public List<FromStaticData> getFromStaticDataForMealsTypeOnly() {
		return mealDietPlanDao.getFromStaticDataForMealsTypeOnly();
	}

	@Override
	public boolean saveMealDietPlan(MealDietPlan mdplan, String[] iteamValuesId, String[] quntityValuesId,
			String[] totalcalValuesId, String user,String[] unitsValuesId) {
		boolean flag=false;
		mdplan.setCreatedBy(user);
		mdplan.setCreatedOn(new Date());
	
		List<MealDietPlanItem> milist=new ArrayList<>();
		for(int i=0; iteamValuesId.length>i;i++) {
				MealDietPlanItem mipojo=new MealDietPlanItem();
				mipojo.setItemName(iteamValuesId[i]);
				mipojo.setItemQuantity(Double.parseDouble(quntityValuesId[i]));
				mipojo.setTotalCalories(Double.parseDouble(totalcalValuesId[i]));
				if(quntityValuesId[i]!=null &&quntityValuesId[i]!="") {
				UnitsMaster pojo=null;
				 pojo =mealDietPlanDao.getUnitsMasterWithUnitcode(unitsValuesId[i]);
				 mipojo.setUnits(pojo);
				}else {
					mipojo.setUnits(null);
				}
				mipojo.setCreatedBy(user);
				mipojo.setCreatedOn(new Date());
				mipojo.setMealDietPlan(mdplan);
		    	milist.add(mipojo);
		}
		flag=mealDietPlanDao.saveMealDietPlanData(mdplan,milist);
		
		return flag;
	}

	@Override
	public MealDietPlan getMealDietPlanWithId(long id) {
		return mealDietPlanDao.getMealDietPlanWithId(id);
	}

	@Override
	public List<MealDietPlanItem> getMealDietPlanItemWithMapId(MealDietPlan mdplan) {
		return mealDietPlanDao.getMealDietPlanItemWithMapId(mdplan);
	}

	@Override
	public MealDietPlan getMealDietPlanWithMealTitle(String mealTitle) {
		return mealDietPlanDao.getMealDietPlanWithMealTitle(mealTitle);
	}
	/** 
     * This method get UnitsMaster List
     * @return List
     */
	@Override
	public List<UnitsMaster> getUnitsMasterData() {
		return mealDietPlanDao.getUnitsMasterData();
	}
}
