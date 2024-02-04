package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.MealMenuReportDao;
import com.covideinfo.dto.MealDietPlanItemDto;
import com.covideinfo.dto.MealMenuReportsDetailsDto;
import com.covideinfo.dto.StudyLevelConfiguredMealsDietDetailsDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.dto.StudyPeriodsDto;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyMealTimePointDiet;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.UserMaster;

@Repository("mealMenuReportDao")
public class MealMenuReportDaoImpl extends AbstractDao<Long, StudyMaster> implements MealMenuReportDao {

	@SuppressWarnings("unchecked")
	@Override
	public MealMenuReportsDetailsDto getMealMenuReportDetails() {
		MealMenuReportsDetailsDto mmRdDto = null;
		List<StudyMasterDto> smList = null;
		try {
			smList = getSession().createCriteria(StudyMaster.class)
					       .setProjection(Projections.projectionList()
					    		.add(Projections.property("id"), "id")
					    		.add(Projections.property("projectNo"), "projectNo"))
					       .setResultTransformer(Transformers.aliasToBean(StudyMasterDto.class)).list();
				
			 mmRdDto = new MealMenuReportsDetailsDto();
			 mmRdDto.setSmList(smList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mmRdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MealMenuReportsDetailsDto getStudyLevelMealReportDetails(Long studyId) {
		MealMenuReportsDetailsDto mmRdDto = null;
		List<StudyPeriodsDto> spmList = null;
		List<StudyLevelConfiguredMealsDietDetailsDto> slcmdList = null;
		try {
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study.id", studyId))
					   .createAlias("study", "std")
				       .setProjection(Projections.projectionList()
				    		.add(Projections.property("id"), "id")
				    		.add(Projections.property("periodName"), "periodName")
				    		.add(Projections.property("std.id"), "studyId"))
				       .setResultTransformer(Transformers.aliasToBean(StudyPeriodsDto.class)).list();
		
			
			slcmdList = getSession().createCriteria(StudyMealTimePointDiet.class)
					       .add(Restrictions.eq("study.id", studyId))
					       .createAlias("studyPeriod", "period")
					       .createAlias("mealtimePoint", "meal")
					       .createAlias("mealDietPlan", "deitPlan")
					       .createAlias("mealDietPlan.mealType", "formStatic")
					       .setProjection(Projections.projectionList()
					    		.add(Projections.property("id"), "id")
					    		.add(Projections.property("period.id"), "periodId")
					    		.add(Projections.property("period.periodName"), "periodName")
					    		.add(Projections.property("meal.id"), "mealId")
					    		.add(Projections.property("meal.sign"), "tpSign")
					    		.add(Projections.property("meal.timePoint"), "timePoint")
					    		.add(Projections.property("deitPlan.id"), "mealDietId")
					    		.add(Projections.property("deitPlan.mealTitle"), "mealTitle")
					    		.add(Projections.property("formStatic.fieldValue"), "mealType"))
					       .setResultTransformer(Transformers.aliasToBean(StudyLevelConfiguredMealsDietDetailsDto.class)).list();
			
			 mmRdDto = new MealMenuReportsDetailsDto();
			 mmRdDto.setSpmList(spmList);
			 mmRdDto.setSlcmdList(slcmdList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mmRdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MealMenuReportsDetailsDto getMealMenuItemsRecordsList(List<Long> dietMenuIds, Long userId, Long studyId) {
		MealMenuReportsDetailsDto mmRdDto = null;
		List<MealDietPlanItemDto> mealDietItemsList = null;
		String studyName = null;
		UserMaster user = null;
		try {
			studyName = (String) getSession().createCriteria(StudyMaster.class)
					       .add(Restrictions.eq("id", studyId))
					       .setProjection(Projections.property("projectNo")).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					    .add(Restrictions.eq("id", userId)).uniqueResult();
			
			mealDietItemsList = getSession().createCriteria(MealDietPlanItem.class)
					.add(Restrictions.in("mealDietPlan.id", dietMenuIds))
					.createAlias("units", "u", JoinType.LEFT_OUTER_JOIN)
					.createAlias("mealDietPlan", "mdp")
		            .setProjection(Projections.projectionList()
	                	.add(Projections.property("id"), "id")
	                	.add(Projections.property("itemName"), "itemName")
	                	.add(Projections.property("itemQuantity"), "quantity")
	                	.add(Projections.property("totalCalories"), "totalCalries")
	                	.add(Projections.property("mdp.id"), "mealDietplanId")
	                	.add(Projections.property("u.unitsCode"), "unitsCode"))
		            .setResultTransformer(Transformers.aliasToBean(MealDietPlanItemDto.class)).list();
			
			mmRdDto = new MealMenuReportsDetailsDto();
			mmRdDto.setStudyName(studyName);
			mmRdDto.setMealDietItemsList(mealDietItemsList);
			mmRdDto.setUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mmRdDto;
	}

}
