package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.AllowMealsDao;
import com.covideinfo.dto.AllowMealsDataDto;
import com.covideinfo.dto.AllowMealsRecordsDto;
import com.covideinfo.dto.AllowStudySubjectMealsDto;
import com.covideinfo.dto.MealsTpsDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.dto.StudyPeriodsDto;
import com.covideinfo.dto.SubjectsDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.AllowStudySubjectMeals;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserMaster;

@Repository("allowMealsDao")
public class AllowMealsDaoImpl extends AbstractDao<Long, StudyMaster> implements AllowMealsDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMasterDto> getStudyMasterList() {
		return getSession().createCriteria(StudyMaster.class)
				.setProjection(Projections.projectionList()
						.add(Projections.property("id"), "id")
						.add(Projections.property("projectNo"), "projectNo"))
				.setResultTransformer(Transformers.aliasToBean(StudyMasterDto.class)).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public AllowMealsRecordsDto getAllowMealsRecords(Long projectId) {
		AllowMealsRecordsDto almrDto = null;
		List<StudyPeriodsDto> spmList = null;
		List<MealsTpsDto> mealsList = null;
		List<SubjectsDto> subjectList = null;
		List<AllowStudySubjectMealsDto> allowMealsList = null;
		Long treatmentId = null;
		StatusMaster status = null;
		try {
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					     .add(Restrictions.eq("study.id", projectId))
					     .setProjection(Projections.projectionList()
					    	.add(Projections.property("id"), "id")
					    	.add(Projections.property("periodName"), "periodName"))
					     .setResultTransformer(Transformers.aliasToBean(StudyPeriodsDto.class)).list();
			
			subjectList = getSession().createCriteria(StudySubjects.class)
					       .add(Restrictions.eq("study.id", projectId))
					       .add(Restrictions.eq("subjectStatus", "active"))
					       .setProjection(Projections.projectionList()
					    		 .add(Projections.property("id"), "subjectId")
					    		 .add(Projections.property("subjectNo"), "subjectNo"))
					       .setResultTransformer(Transformers.aliasToBean(SubjectsDto.class)).list();
			
			treatmentId = (Long) getSession().createCriteria(TreatmentInfo.class)
					         .add(Restrictions.eq("study.id", projectId))
					         .setProjection(Projections.min("id")).uniqueResult();
			
			if(treatmentId != null) {
				mealsList = getSession().createCriteria(MealsTimePoints.class)
						       .add(Restrictions.eq("study.id", projectId))
						       .add(Restrictions.eq("treatmentInfo.id", treatmentId))
						       .setProjection(Projections.projectionList()
						    		  .add(Projections.property("id"), "id")
						    		  .add(Projections.property("sign"), "tpSign")
						    		  .add(Projections.property("timePoint"), "timePoint"))
						       .setResultTransformer(Transformers.aliasToBean(MealsTpsDto.class)).list();
						       
			}
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					      .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			allowMealsList = getSession().createCriteria(AllowStudySubjectMeals.class)
					              .add(Restrictions.eq("studyId.id", projectId))
					              .add(Restrictions.eq("status.id", status.getId()))
					              .createAlias("mealId", "meal")
					              .createAlias("period", "p")
					              .setProjection(Projections.projectionList()
					            		 .add(Projections.property("id"), "id")
					            		 .add(Projections.property("p.periodName"), "periodName")
					            		 .add(Projections.property("meal.sign"), "tpSign")
					            		 .add(Projections.property("meal.timePoint"), "timePoint")
					            		 .add(Projections.property("subjects"), "subjects")
					            		 .add(Projections.property("allowedTime"), "allowedTime"))
					              .setResultTransformer(Transformers.aliasToBean(AllowStudySubjectMealsDto.class)).list();
			
			 almrDto = new AllowMealsRecordsDto();
			 almrDto.setSpmList(spmList);
			 almrDto.setSubjectList(subjectList);
			 almrDto.setMealsList(mealsList);
			 almrDto.setAllowMealsList(allowMealsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return almrDto;
	}

	@Override
	public String saveAllowMealsRecord(Long userId, AllowMealsDataDto almDto) {
		String result ="Failed";
		StudyPeriodMaster spm = null;
		MealsTimePoints meal = null;
		StudyMaster  study = null;
		StatusMaster status = null;
		UserMaster user = null;
		try {
			if(almDto != null) {
				status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						    .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
				
				user = (UserMaster) getSession().createCriteria(UserMaster.class)
						  .add(Restrictions.eq("id", userId)).uniqueResult();
				
				AllowStudySubjectMeals alsmPojo = (AllowStudySubjectMeals) getSession().createCriteria(AllowStudySubjectMeals.class)
						                                .add(Restrictions.eq("studyId.id", almDto.getProjectId()))
						                                .add(Restrictions.eq("period.id", almDto.getPeriodId()))
						                                .add(Restrictions.eq("mealId.id", almDto.getMealId())).uniqueResult();
				if(alsmPojo != null) {
					if(!alsmPojo.getSubjects().equals(almDto.getSubjects())) {
						alsmPojo.setSubjects(almDto.getSubjects());
						alsmPojo.setUpdatedBy(user.getUsername());
						alsmPojo.setUpdatedOn(new Date());
						alsmPojo.setStatus(status);
						getSession().merge(alsmPojo);
						result ="Success";
					}else result ="Exists";
				}else {
					study = (StudyMaster) getSession().createCriteria(StudyMaster.class)
						    .add(Restrictions.eq("id", almDto.getProjectId())).uniqueResult();
				
					spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
						  .add(Restrictions.eq("id", almDto.getPeriodId())).uniqueResult();
				
					meal = (MealsTimePoints) getSession().createCriteria(MealsTimePoints.class)
						   .add(Restrictions.eq("id", almDto.getMealId())).uniqueResult();
					
					AllowStudySubjectMeals alsm = new AllowStudySubjectMeals();
					alsm.setAllowedTime(almDto.getAllowedTime());
					alsm.setCreatedBy(user.getUsername());
					alsm.setCreatedOn(new Date());
					alsm.setMealId(meal);
					alsm.setPeriod(spm);
					alsm.setReason(almDto.getReason());
					alsm.setStudyId(study);
					alsm.setSubjects(almDto.getSubjects());
					alsm.setStatus(status);
					long alsmNo = (long) getSession().save(alsm);
					if(alsmNo > 0)
						result ="Success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public String inactiveAllowMealRecord(Long inactId, Long userId) {
		String result ="Failed";
		StatusMaster status = null;
		AllowStudySubjectMeals alsmPojo = null;
		UserMaster user = null;
		try {
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
				    .add(Restrictions.eq("statusCode", StudyStatus.INACTIVE.toString())).uniqueResult();
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					  .add(Restrictions.eq("id", userId)).uniqueResult();
			
			alsmPojo = (AllowStudySubjectMeals) getSession().createCriteria(AllowStudySubjectMeals.class)
                    .add(Restrictions.eq("id", inactId)).uniqueResult();
			if(alsmPojo != null) {
				alsmPojo.setUpdatedBy(user.getUsername());
				alsmPojo.setUpdatedOn(new Date());
				alsmPojo.setStatus(status);
				getSession().merge(alsmPojo);
				result = "Success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
