package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.MealDietPlanDao;
import com.covideinfo.model.FromStaticData;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.UnitsMaster;

@Repository("mealDietPlanDao")
public class MealDietPlanDaoImpl extends AbstractDao<Long,MealDietPlan> implements MealDietPlanDao   {

	@SuppressWarnings("unchecked")
	@Override
	public List<MealDietPlan> getMealDietPlanList() {
		List<MealDietPlan> list=getSession().createCriteria(MealDietPlan.class).list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FromStaticData> getFromStaticDataForMealsTypeOnly() {
		List<FromStaticData> list=getSession().createCriteria(FromStaticData.class)
				.add(Restrictions.eq("fieldName", "MEALSTTYPE")).list();
		return list;
	}

	@Override
	public boolean saveMealDietPlanData(MealDietPlan mdplan, List<MealDietPlanItem> milist) {
		boolean flag=false;
		try {
			getSession().save(mdplan);
			for(MealDietPlanItem adv:milist) {
				getSession().save(adv);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public MealDietPlan getMealDietPlanWithId(long id) {
		MealDietPlan pojo=null;
		pojo=(MealDietPlan) getSession().createCriteria(MealDietPlan.class)
				.add(Restrictions.eq("id", id)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MealDietPlanItem> getMealDietPlanItemWithMapId(MealDietPlan mdplan) {
		List<MealDietPlanItem> list=new ArrayList<>();
		list=getSession().createCriteria(MealDietPlanItem.class)
				.add(Restrictions.eq("mealDietPlan", mdplan)).list();
		return list;
	}

	@Override
	public MealDietPlan getMealDietPlanWithMealTitle(String mealTitle) {
		MealDietPlan pojo=null;
		pojo=(MealDietPlan) getSession().createCriteria(MealDietPlan.class)
				.add(Restrictions.eq("mealTitle", mealTitle)).uniqueResult();
		return pojo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnitsMaster> getUnitsMasterData() {
		List<UnitsMaster> list=new ArrayList<>();
		list=getSession().createCriteria(UnitsMaster.class).list();
		return list;
	}

	@Override
	public UnitsMaster getUnitsMasterWithUnitcode(String string) {
		UnitsMaster pojo=null;
		pojo=(UnitsMaster) getSession().createCriteria(UnitsMaster.class)
				.add(Restrictions.eq("unitsCode", string)).uniqueResult();
		return pojo;
	}

}
