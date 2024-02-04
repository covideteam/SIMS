package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudySubjectMealConsumptionDao;
import com.covideinfo.dto.MealDietPlanItemDto;
import com.covideinfo.dto.StudyMealConsumptionDietDto;
import com.covideinfo.dto.StudyMealConsumptionItemsDataDto;
import com.covideinfo.dto.StudyMealTimepointConfigDietDto;
import com.covideinfo.dto.StudyMealsConsumptionDietDtoDetails;
import com.covideinfo.dto.SubjectMealTimePointsDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.MealDietPlanItem;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyMealTimePointDiet;
import com.covideinfo.model.StudySubjectDeviations;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectMealsLeftQuatityDetails;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.UserMaster;

@Repository("studySubjectMealConsumptionDao")
class StudySubjectMealConsumptionDaoImpl extends AbstractDao<Long, StudyMealTimePointDiet> implements StudySubjectMealConsumptionDao {

	

	@SuppressWarnings("unchecked")
	@Override
	public StudyMealConsumptionDietDto getStudyMealConsumptionDietDtoDetails() {
		List<StudyMaster> smList = null;
		StudyMealConsumptionDietDto smcdDto = null;
		StatusMaster approvedStatus = null;
		try {
			approvedStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					            .add(Restrictions.eq("statusCode", StudyStatus.APPROVED.toString())).uniqueResult();
			smList = getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("studyStatus.id", approvedStatus.getId())).list();
					
			smcdDto = new StudyMealConsumptionDietDto();
			smcdDto.setSmList(smList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smcdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyMealsConsumptionDietDtoDetails getStudyMealsConsumptionDietDtoDetails(Long studyId) {
		StudyMealsConsumptionDietDtoDetails smcdDto = null;
		List<MealDietPlanItemDto> mealdietItemList = null;
		List<SubjectMealTimePointsDto> smtpDataList = null;
		List<Long> stdSubIds = null;
		Set<Long> mealIds = new HashSet<>();
		Set<Long> dietPlanIds = new HashSet<>();
		List<StudyMealTimepointConfigDietDto> stdMealTpConfigDtoList = null;
		Map<Long, Set<Long>> mealDietPlanIdsMap = new HashMap<>();//MealId, List of dietplanitemIds
		List<Long> subjectMealIds = null;
		Set<Long> tempList = null;
		try {
			subjectMealIds = getSession().createCriteria(SubjectMealsLeftQuatityDetails.class)
					 .add(Restrictions.eq("studyId.id", studyId))
					 .setProjection(Projections.property("subMealId.id")).list();
			
			stdSubIds = getSession().createCriteria(StudySubjects.class)
					         .add(Restrictions.eq("study.id", studyId))
					         .setProjection(Projections.property("id")).list();
			
			if(stdSubIds != null && stdSubIds.size() > 0) {
				Criteria criteria = getSession().createCriteria(SubjectMealsTimePointsData.class, "smtpData")
						 			.add(Restrictions.in("subject.id", stdSubIds))
						 			.add(Restrictions.eq("consumption", "No"));
				if(subjectMealIds.size() > 0) {
					criteria.add(Restrictions.not(Restrictions.in("id", subjectMealIds)));
				}
				criteria.createAlias("mealsTimePoint", "mtp")
	 					.createAlias("subject", "sub")
	 					.createAlias("studyPeriodMaster", "sp")
	 					.setProjection(Projections.projectionList()
				        .add(Projections.property("smtpData.id"),"id")
				        .add(Projections.property("mtp.id"),"mealId")   
				        .add(Projections.property("sp.periodName"),"period")                
				        .add(Projections.property("mtp.timePoint"),"timePoint")
				        .add(Projections.property("mtp.sign"),"sign")
				        .add(Projections.property("sub.subjectNo"),"subjectNo")
				        ).setResultTransformer(Transformers.aliasToBean(SubjectMealTimePointsDto.class));
				
				smtpDataList = criteria.list();
				if(smtpDataList != null && smtpDataList.size() > 0) {
					for(SubjectMealTimePointsDto smtpd : smtpDataList) {
						mealIds.add(smtpd.getMealId());
					}
				}
			}
			if(mealIds.size() > 0) {
				Criteria cri = getSession().createCriteria(StudyMealTimePointDiet.class)
									.add(Restrictions.in("mealtimePoint.id", mealIds))
									.setProjection(Projections.projectionList()
											.add(Projections.property("mealDietPlan.id"),"dietPlanId")
											.add(Projections.property("mealtimePoint.id"), "mealId"))
									.setResultTransformer(Transformers.aliasToBean(StudyMealTimepointConfigDietDto.class));
				
				stdMealTpConfigDtoList = cri.list();
				if(stdMealTpConfigDtoList != null) {
					for(StudyMealTimepointConfigDietDto smcd : stdMealTpConfigDtoList) {
						dietPlanIds.add(smcd.getDietPlanId());
						if(mealDietPlanIdsMap.containsKey(smcd.getMealId())) {
							tempList = mealDietPlanIdsMap.get(smcd.getMealId());
							tempList.add(smcd.getDietPlanId());
							mealDietPlanIdsMap.put(smcd.getMealId(), tempList);
						}else {
							tempList = new HashSet<>();
							tempList.add(smcd.getDietPlanId());
							mealDietPlanIdsMap.put(smcd.getMealId(), tempList);
						}
					}
				}
				
				if(dietPlanIds != null && dietPlanIds.size() > 0) {
					Criteria mealdietCriteria = getSession().createCriteria(MealDietPlanItem.class, "mdpItem")
							.add(Restrictions.in("mealDietPlan.id", dietPlanIds));
					
					mealdietCriteria.createAlias("units", "u", JoinType.LEFT_OUTER_JOIN)
					.createAlias("mealDietPlan", "mdp")
					.setProjection(Projections.projectionList()
						      .add(Projections.property("mdpItem.id"), "id")
						      .add(Projections.property("mdpItem.itemName"), "itemName")
						    .add(Projections.property("mdpItem.itemQuantity"), "quantity")
						    .add(Projections.property("mdpItem.totalCalories"), "totalCalries")
						    .add(Projections.property("mdp.id"), "mealDietplanId")
						    .add(Projections.property("u.unitsCode"), "unitsCode")
						    ).setResultTransformer(Transformers.aliasToBean(MealDietPlanItemDto.class));
					
					mealdietItemList = mealdietCriteria.list();

				}
			}
			smcdDto = new StudyMealsConsumptionDietDtoDetails();
			smcdDto.setSubMealTpDataMap(smtpDataList);
			smcdDto.setMealdietList(mealdietItemList);
			smcdDto.setMealDietPlanIdsMap(mealDietPlanIdsMap);
			smcdDto.setSubjectMealIds(subjectMealIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smcdDto;
	}

	/*This method is saving the data of left quantity and left calories of subject left over meals details
	 * and the total left over calories are more than 25% then raise a deviation and saves that deviation record*/
	@Override
	public String saveStudyMealConsumptionDietData(Long userId, StudyMealConsumptionItemsDataDto smcitemData) {
		String result ="Success";
		List<SubjectMealsLeftQuatityDetails> smqdList = new ArrayList<>();
		UserMaster user = null;
		SubjectMealsTimePointsData mealtp = null;
		StudyMaster study = null;
		MealDietPlanItem mdpi = null;
		List<String> strList = smcitemData.getMealDietConfigData();
		try {
			user = (UserMaster)getSession().createCriteria(UserMaster.class)
			                  .add(Restrictions.eq("id", userId)).uniqueResult();
			
			mealtp = (SubjectMealsTimePointsData) getSession().createCriteria(SubjectMealsTimePointsData.class)
					     .add(Restrictions.eq("id", smcitemData.getMealId())).uniqueResult();
			
			study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					    .add(Restrictions.eq("id", smcitemData.getProjectId())).uniqueResult();
			
			if(strList != null && strList.size() > 0) {
				for(String st : strList) {
					if(st != null && !st.equals("")) {
						String[] arr = st.split("\\@@@");
						if(arr.length > 0) {
							mdpi = (MealDietPlanItem) getSession().createCriteria(MealDietPlanItem.class)
									 .add(Restrictions.eq("id", Long.parseLong(arr[0]))).uniqueResult();
							SubjectMealsLeftQuatityDetails smqd = new SubjectMealsLeftQuatityDetails();
							smqd.setCreatedBy(user.getUsername());
							smqd.setCreatedOn(new Date());
							smqd.setDietPlanItemId(mdpi);
							smqd.setLeftQuantity(Double.parseDouble(arr[1]));
							smqd.setLeftQuantityCalories(Double.parseDouble(arr[2]));
							smqd.setSubMealId(mealtp);
							smqd.setReason("");
							smqd.setStudyId(study);
							smqdList.add(smqd);
						}
					}
				}
			}
			if(smqdList.size() > 0) {
				for(SubjectMealsLeftQuatityDetails smlqd : smqdList) {
					getSession().save(smlqd);
				}
			}
			if(result.equals("Success")) {
				double totalCalories = smcitemData.getTotalLeftCalories();
				double reference = 100;
				boolean isAbove25 = isAbove25Percent(totalCalories, reference);
	            if (isAbove25) {
	            	GlobalActivity ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
	            			                 .add(Restrictions.eq("activityCode", StudyActivities.MealsCollection.toString())).uniqueResult();
	            	
	            	StatusMaster newStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
							.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
	            	
	            	DeviationMessage dm = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
							.add(Restrictions.eq("developerCode", "MLOD")).uniqueResult();
	            	StudySubjectDeviations ssd = new StudySubjectDeviations();
					ssd.setActivity(ga);
					ssd.setStudyId(smcitemData.getProjectId());
					ssd.setPeriod(mealtp.getStudyPeriodMaster().getPeriodName());
					ssd.setTimePoint(mealtp.getMealsTimePoint().getSign()+mealtp.getMealsTimePoint().getTimePoint());
					ssd.setDeviationRecordId(mealtp.getId());
					ssd.setStatus(newStatus);
					ssd.setCreatedBy(user);
					ssd.setCreatedOn(new Date());
					ssd.setDevMsgId(dm);
					ssd.setSubject(mealtp.getSubject());
					getSession().save(ssd);
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}
		return result;
	}

	private boolean isAbove25Percent(Double totalCalories, double reference) {
		double twentyFivePercent = (reference * 0.25); // Calculate 25% of the reference
        return totalCalories > twentyFivePercent;
	}

	

}
