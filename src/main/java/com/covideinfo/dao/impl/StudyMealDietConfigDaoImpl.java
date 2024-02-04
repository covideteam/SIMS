package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.expression.spel.ast.Projection;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyMealDietConfigDao;
import com.covideinfo.dto.StudyMealTimePointDietDto;
import com.covideinfo.dto.StudyMealsDietConfiguraionDetailsDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.MealDietPlan;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyMealTimePointDiet;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.TreatmentInfo;

@Repository("studyMealDietConfigDao")
public class StudyMealDietConfigDaoImpl extends AbstractDao<Long, StudyMealTimePointDiet> implements StudyMealDietConfigDao {

	@SuppressWarnings("unchecked")
	@Override
	public StudyMealsDietConfiguraionDetailsDto getStudyMealsDietConfiguraionDtoDetails() {
		StudyMealsDietConfiguraionDetailsDto smdcdDto = null;
		List<StudyMaster> smList = null;
		StatusMaster approvedStatus = null;
		try {
			approvedStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					           .add(Restrictions.eq("statusCode", StudyStatus.APPROVED.toString())).uniqueResult();
			smList = getSession().createCriteria(StudyMaster.class)
					    .add(Restrictions.eq("studyStatus.id", approvedStatus.getId())).list();
			smdcdDto = new StudyMealsDietConfiguraionDetailsDto();
			smdcdDto.setSmList(smList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smdcdDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StudyMealsDietConfiguraionDetailsDto getStudyRelatedMealsConfigurationDetails(Long studyId) {
		StudyMealsDietConfiguraionDetailsDto smdcdDto = null;
		List<MealsTimePoints> mealstpList = null;
		List<StudyPeriodMaster> spmList = null;
		List<StudyMealTimePointDiet> smtpDietList = null;
		Long treatmentId = null;
		
		/*Present we are taking only unique time points for treatment one if treatment wise then we have to modify this code.
		 * if mealtype exists apply this here we are not considering treatment only consider time point and sing*/
		List<MealDietPlan> mdplanList = null;
		try {
				spmList = getSession().createCriteria(StudyPeriodMaster.class)
					     .add(Restrictions.eq("study.id", studyId)).list();
				
				mealstpList = getSession().createCriteria(MealsTimePoints.class)
					       .add(Restrictions.eq("study.id", studyId)).list();
				
				mdplanList = getSession().createCriteria(MealDietPlan.class).list();
				
				smtpDietList = getSession().createCriteria(StudyMealTimePointDiet.class)
						           .add(Restrictions.eq("study.id", studyId)).list();
				
				treatmentId = (Long) getSession().createCriteria(TreatmentInfo.class)
						           .add(Restrictions.eq("study.id", studyId))
						           .setProjection(Projections.min("id")).uniqueResult();
				
			smdcdDto = new StudyMealsDietConfiguraionDetailsDto();
			smdcdDto.setMealstpList(mealstpList);
			smdcdDto.setSpmList(spmList);
			smdcdDto.setMdplanList(mdplanList);
			smdcdDto.setSmtpDietList(smtpDietList);
			smdcdDto.setTreatmentId(treatmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return smdcdDto;
	}

	
	/*This method saves the data and update of meal time points wise configured diet plan
	 * 
	 * Saving ==> The data saving happens here for what are the time point is exists for all treatments those many records will saved here
	 * Update ==> If one time point diet plan is updated then that time point related all periods all treatments records will be updating here  */
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean saveStudyMealTimePointDiet(Long projectId, List<StudyMealTimePointDietDto> smtpdDtoList) {
		boolean flag = true;
		StudyMaster sm = null;
		StudyPeriodMaster spm = null;
		MealsTimePoints mtp = null;
		MealDietPlan mdp = null;
		StudyMealTimePointDiet smtpdPojo = null; 
		List<MealsTimePoints> mtpList = null;
		try {
			sm = (StudyMaster)getSession().createCriteria(StudyMaster.class)
					    .add(Restrictions.eq("id", projectId)).uniqueResult();
			for(StudyMealTimePointDietDto smtpDto : smtpdDtoList) {
				
				mtp = (MealsTimePoints) getSession().createCriteria(MealsTimePoints.class)
						.add(Restrictions.eq("id", smtpDto.getMealId())).uniqueResult();
				if(smtpDto.getPeriodId() == 15)
					System.out.println("Period2");
				mtpList = getSession().createCriteria(MealsTimePoints.class)
                        .add(Restrictions.eq("sign", mtp.getSign()))
                        .add(Restrictions.eq("timePoint", mtp.getTimePoint()))
                        .add(Restrictions.eq("study.id", mtp.getStudy().getId())).list();
				
				smtpdPojo = (StudyMealTimePointDiet) getSession().createCriteria(StudyMealTimePointDiet.class)
					       .add(Restrictions.eq("study.id", projectId))
					       .add(Restrictions.eq("studyPeriod.id", smtpDto.getPeriodId()))
					       .add(Restrictions.eq("mealtimePoint.id", mtp.getId())).uniqueResult();
				
				
				mdp = (MealDietPlan) getSession().createCriteria(MealDietPlan.class)
						  .add(Restrictions.eq("id", smtpDto.getDietId())).uniqueResult();
				
				spm = (StudyPeriodMaster) getSession().createCriteria(StudyPeriodMaster.class)
						.add(Restrictions.eq("id", smtpDto.getPeriodId())).uniqueResult();
				if(smtpdPojo == null) {
					if(mtpList != null && mtpList.size() > 0) {
						for(MealsTimePoints meal : mtpList) {
							StudyMealTimePointDiet smtpd = new StudyMealTimePointDiet();
							smtpd.setCreatedBy(smtpDto.getUserName());
							smtpd.setCreatedOn(new Date());
							smtpd.setMealDietPlan(mdp);
							smtpd.setMealtimePoint(meal);
							smtpd.setReason("");
							smtpd.setStudy(sm);
							smtpd.setStudyPeriod(spm);
							smtpd.setUpdatedBy("");
							long smtpdNo = (long) getSession().save(smtpd);
							if(smtpdNo <= 0)
								flag = false;
						}
					}
				}else {
					if(mtpList != null && mtpList.size() > 0) {
						for(MealsTimePoints meal : mtpList) {
							StudyMealTimePointDiet smtpd = (StudyMealTimePointDiet) getSession().createCriteria(StudyMealTimePointDiet.class)
								       .add(Restrictions.eq("study.id", projectId))
								       .add(Restrictions.eq("studyPeriod.id", smtpDto.getPeriodId()))
								       .add(Restrictions.eq("mealtimePoint.id", meal.getId())).uniqueResult();
							
							if(smtpd.getMealDietPlan().getId() != smtpDto.getDietId()) {
								smtpd.setUpdatedBy(smtpDto.getUserName());
								smtpd.setUpdatedOn(new Date());
								smtpd.setMealDietPlan(mdp);
								getSession().update(smtpd);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return flag;
	}

}
