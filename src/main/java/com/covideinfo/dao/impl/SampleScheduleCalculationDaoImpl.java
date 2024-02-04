package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.SampleScheduleCalculationDao;
import com.covideinfo.dto.SubjectTimePointsDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DeviationMessage;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudySubjectDeviations;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.UserMaster;
import com.covideinfo.model.VitalTimePoints;

@Repository("sampleScheduleCalculationDao")
public class SampleScheduleCalculationDaoImpl extends AbstractDao<Long, StudyMaster> implements SampleScheduleCalculationDao {

	@SuppressWarnings("unchecked")
	@Override
	public SubjectTimePointsDto getSubjectTimePointsDtoDetails(SubjectDoseTimePoints sdtp, Long userId) {
		SubjectTimePointsDto stpDto = null;
		List<SubjectMealsTimePointsData> mealsDtaList = null;
		List<SubjectSampleCollectionTimePointsData> samDataList = null;
		List<SubjectVitalTimePointsData> svDataList = null;
		List<Long> mealIds = null;
		List<Long> sampleIds = null;
		List<Long> vitalIds = null;
		UserMaster user = null;
		DeviationMessage dm;
		StatusMaster newStatus = null;
		GlobalActivity mealActvity = null;
		GlobalActivity sampleActivity = null;
		GlobalActivity vitalCollectionActivity = null;
		
		try {
			
			user = (UserMaster) getSession().createCriteria(UserMaster.class)
					   .add(Restrictions.eq("id", userId)).uniqueResult();
			
			mealIds = getSession().createCriteria(MealsTimePoints.class)
					    .add(Restrictions.eq("sign", "-"))
					    .add(Restrictions.eq("study", sdtp.getPeriod().getStudy()))
					    .add(Restrictions.eq("treatmentInfo", sdtp.getDoseTimePoints().getTreatmentInfo()))
					    .setProjection(Projections.property("id")).list();
			
			if(mealIds != null && mealIds.size() > 0) {
				mealsDtaList = getSession().createCriteria(SubjectMealsTimePointsData.class)
				           .add(Restrictions.eq("subject", sdtp.getStudySubjects()))
				           .add(Restrictions.eq("studyPeriodMaster", sdtp.getPeriod()))
				           .add(Restrictions.in("mealsTimePoint.id", mealIds)).list();
			}
			
			sampleIds = getSession().createCriteria(SampleTimePoints.class)
					       .add(Restrictions.eq("study", sdtp.getPeriod().getStudy()))
					       .add(Restrictions.eq("treatmentInfo", sdtp.getDoseTimePoints().getTreatmentInfo()))
					       .add(Restrictions.eq("sign", "-"))
					       .setProjection(Projections.property("id")).list();
			
			if(sampleIds != null && sampleIds.size() > 0) {
				samDataList = getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
						        .add(Restrictions.eq("studyPeriodMaster", sdtp.getPeriod()))
						        .add(Restrictions.eq("subject", sdtp.getStudySubjects()))
						        .add(Restrictions.in("sampleTimePoint.id", sampleIds)).list();
			}
			
			vitalIds = getSession().createCriteria(VitalTimePoints.class)
					       .add(Restrictions.eq("study", sdtp.getPeriod().getStudy()))
					       .add(Restrictions.eq("treatmentInfo", sdtp.getDoseTimePoints().getTreatmentInfo()))
					       .add(Restrictions.eq("sign", "-"))
					       .setProjection(Projections.property("id")).list();
			
			if(vitalIds != null && vitalIds.size() > 0) {
				svDataList = getSession().createCriteria(SubjectVitalTimePointsData.class)
						        .add(Restrictions.eq("study", sdtp.getPeriod().getStudy()))
						        .add(Restrictions.eq("subject", sdtp.getStudySubjects()))
						        .add(Restrictions.eq("period", sdtp.getPeriod()))
						        .add(Restrictions.in("timepoint.id", vitalIds)).list();
			}
			newStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					         .add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
			
			dm = (DeviationMessage) getSession().createCriteria(DeviationMessage.class)
					   .add(Restrictions.eq("developerCode", "TPD")).uniqueResult();
			
			mealActvity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
			              .add(Restrictions.eq("activityCode", StudyActivities.MealsCollection.toString())).uniqueResult();
			
			sampleActivity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
		              .add(Restrictions.eq("activityCode", StudyActivities.SampleCollection.toString())).uniqueResult();
			
			vitalCollectionActivity = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
		              .add(Restrictions.eq("activityCode", StudyActivities.StudyExecutionVitals.toString())).uniqueResult();
			
			
			stpDto = new SubjectTimePointsDto();
			stpDto.setMealsDtaList(mealsDtaList);
			stpDto.setSamDataList(samDataList);
			stpDto.setSvDataList(svDataList);
			stpDto.setUser(user);
			stpDto.setDm(dm);
			stpDto.setMealActvity(mealActvity);
			stpDto.setNewStatus(newStatus);
			stpDto.setSampleActivity(sampleActivity);
			stpDto.setVitalCollectionActivity(vitalCollectionActivity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stpDto;
	}

	@Override
	public String updateScheduledTimePointsData(List<SubjectMealsTimePointsData> updateMealsList,
			List<SubjectSampleCollectionTimePointsData> updateSamDataList,
			List<SubjectVitalTimePointsData> updatesvDataList, List<StudySubjectDeviations> ssdList) {
		String result ="Failed";
		boolean mealsFlag = false;
		boolean samplesFlag = false;
		boolean vitalsFlag = false;
		try {
			if(updateMealsList.size() > 0) {
				for(SubjectMealsTimePointsData smtpd : updateMealsList) {
					getSession().update(smtpd);
					mealsFlag = true; 
				}
			}else mealsFlag = true;
			if(updateSamDataList.size() > 0) {
				for(SubjectSampleCollectionTimePointsData ssctpd : updateSamDataList) {
					getSession().update(ssctpd);
					samplesFlag = true; 
				}
			}else samplesFlag = true;
			if(updatesvDataList.size() > 0) {
				for(SubjectVitalTimePointsData svtpd : updatesvDataList) {
					getSession().update(svtpd);
					vitalsFlag = true; 
				}
			}else vitalsFlag = true;
			if(mealsFlag && samplesFlag && vitalsFlag) {
				if(ssdList != null && ssdList.size() > 0) {
					for(StudySubjectDeviations sd : ssdList) {
						if(sd.getActivity().getActivityCode().equals(StudyActivities.MealsCollection.toString())) {
							SubjectMealsTimePointsData smtpData = (SubjectMealsTimePointsData)getSession().createCriteria(SubjectMealsTimePointsData.class)
									                                   .add(Restrictions.eq("id", sd.getDeviationRecordId())).uniqueResult();
						    if(smtpData != null) {
						    	smtpData.setDeviation(sd.getDeviationTime());
						    	smtpData.setUpdatedBy(sd.getCreatedBy());
						    	smtpData.setUpdatedOn(new Date());
						    	smtpData.setUpdateReason("Schedular Deviation Happend.");
						    	getSession().merge(smtpData);
						    }
							getSession().save(sd);
						}else if(sd.getActivity().getActivityCode().equals(StudyActivities.SampleCollection.toString())) {
							SubjectSampleCollectionTimePointsData ssctpData = (SubjectSampleCollectionTimePointsData) getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
									                           .add(Restrictions.eq("id", sd.getDeviationRecordId())).uniqueResult();
							if(ssctpData != null) {
								ssctpData.setDeviation(sd.getDeviationTime());
								ssctpData.setDiviationMessage(sd.getDevMsgId());
								ssctpData.setUpdatedBy(sd.getCreatedBy());
								ssctpData.setUpdatedOn(new Date());
								ssctpData.setUpdateReason("Schedular Deviation Happend.");
						    	getSession().merge(ssctpData);
							}
							getSession().save(sd);
						}else if(sd.getActivity().getActivityCode().equals(StudyActivities.StudyExecutionVitals.toString())) {
							SubjectVitalTimePointsData svTpData = (SubjectVitalTimePointsData) getSession().createCriteria(SubjectVitalTimePointsData.class)
			                           .add(Restrictions.eq("id", sd.getDeviationRecordId())).uniqueResult();
							if(svTpData != null) {
								svTpData.setDeviation(sd.getDeviationTime());
								svTpData.setUpdatedBy(sd.getCreatedBy());
								svTpData.setUpdatedOn(new Date());
								svTpData.setUpdateReason("Schedular Deviation Happend.");
								getSession().merge(svTpData);
							}
							getSession().save(sd);
						}
						
					}
				}
				result = "success";
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	

}
