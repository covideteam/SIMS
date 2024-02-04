package com.covideinfo.dao.impl;

import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyActivityTimePointsRetrivingDao;
import com.covideinfo.dto.StudyActivityTimePointsRetrivingDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificGroupDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyActivityTimePoints;
import com.covideinfo.model.StudyActivityTimePointsCompletionData;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.VitalTimePoints;

@Repository("studyActivityTimePointsRetrivingDao")
public class StudyActivityTimePointsRetrivingDaoImpl extends AbstractDao<Long, StudyMaster> implements StudyActivityTimePointsRetrivingDao {

	@SuppressWarnings("unchecked")
	@Override
	public StudyActivityTimePointsRetrivingDto getStudyActivityTimePointsRetrivingDtoDetails(Long studyId,
			String subjectNo, Long activityId, long languageId) {
		StudyActivityTimePointsRetrivingDto satprDto = null;
		StudyMaster sm = null;
		GlobalActivity ga = null;
		StudySubjects subject = null;
		TreatmentInfo trinfo = null;
		StudyPeriodMaster spm = null;
		StatusMaster status = null;
		SubjectRandamization ssr = null;
		LanguageSpecificGlobalActivityDetails lsga = null;
		StudyActivities sa = null;
		List<Long> paramIds = null;
		List<LanguageSpecificGlobalParameterDetails> lspgpdList = null;
		List<StudyActivityTimePoints> satpList = null;
		List<SubjectDoseTimePoints> dosetpList = null;
		try {
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", studyId)).uniqueResult();
			
			ga = (GlobalActivity) getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.eq("id", activityId)).uniqueResult();
			
			subject = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("subjectNo", subjectNo)).uniqueResult();
			
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			if(subject != null) {
				spm = (StudyPeriodMaster) getSession().createCriteria(StudyGroupPeriodMaster.class)
						.add(Restrictions.eq("studyId", studyId))
						.add(Restrictions.eq("studyGroup", subject.getGroupId()))
						.add(Restrictions.eq("periodStatus", status))
						.setProjection(Projections.property("period")).uniqueResult();
				
				if(spm != null) {
					
					ssr = (SubjectRandamization) getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.eq("period", spm))
							.add(Restrictions.eq("subjectNo", subjectNo)).uniqueResult();
							
					if(ssr != null) {
						trinfo = ssr.getTreatmentInfo();
						
						if(trinfo != null) {
							List<Long> tpIds = null;
							tpIds = getSession().createCriteria(DoseTimePoints.class)
									.add(Restrictions.eq("study", sm))
									.add(Restrictions.eq("treatmentInfo", trinfo))
									.setProjection(Projections.property("id")).list();
							
							if(tpIds != null && tpIds.size() > 0) {
								dosetpList = getSession().createCriteria(SubjectDoseTimePoints.class)
										.add(Restrictions.eq("studySubjects", subject))
										.add(Restrictions.in("doseTimePoints.id", tpIds)).list();
							}
						}
						
					}
					
				}
				
				lsga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
						.add(Restrictions.eq("globalActivity", ga))
						.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
				
				sa = (StudyActivities) getSession().createCriteria(StudyActivities.class)
						.add(Restrictions.eq("sm", sm))
						.add(Restrictions.eq("studyPeriod", spm))
						.add(Restrictions.eq("activityId", ga))
						.add(Restrictions.eq("treatment", trinfo)).uniqueResult();
				
				if(sa != null) {
					paramIds = getSession().createCriteria(StudyActivityParameters.class)
							.add(Restrictions.eq("studyActivity", sa))
							.setProjection(Projections.property("parameterId.id")).list();
					if(paramIds != null) {
						lspgpdList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
								.add(Restrictions.in("parameterId.id", paramIds))
								.add(Restrictions.eq("inlagId.id", languageId)).list();
					}
					satpList = getSession().createCriteria(StudyActivityTimePoints.class)
							.add(Restrictions.eq("studyActivity", sa)).list();
				}
			}
			
			satprDto = new StudyActivityTimePointsRetrivingDto();
			satprDto.setLsga(lsga);
			satprDto.setLspgpdList(lspgpdList);
			satprDto.setSatpList(satpList);
			satprDto.setDosetpList(dosetpList);
			satprDto.setSa(sa);
			satprDto.setSpm(spm);
			satprDto.setTfinfo(trinfo);
			satprDto.setGa(ga);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return satprDto;
	}

	@Override
	public StudyActivityTimePointsCompletionData getStudyActivityTimePointsCompletionData(long studyActid, long studyActTpId, Long periodId,
			long treatmentId, Long studyId) {
		return (StudyActivityTimePointsCompletionData) getSession().createCriteria(StudyActivityTimePointsCompletionData.class)
				.add(Restrictions.eq("studyActivityId", studyActid))
				.add(Restrictions.eq("stuydActTimePointId", studyActTpId))
				.add(Restrictions.eq("periodId", periodId))
				.add(Restrictions.eq("treatmentId", treatmentId))
				.add(Restrictions.eq("studyId", studyId))
				.add(Restrictions.eq("status", "Completed")).uniqueResult();
	}

	@Override
	public VitalTimePoints getDosingTimePointsRecord(String timePoint, TreatmentInfo tfinfo,
			Long studyId) {
		return (VitalTimePoints) getSession().createCriteria(VitalTimePoints.class)
				.add(Restrictions.eq("study.id", studyId))
				.add(Restrictions.eq("treatmentInfo", tfinfo))
				.add(Restrictions.eq("timePoint", timePoint)).uniqueResult();
	}

	@Override
	public LanguageSpecificGroupDetails getGloablActivityDetails(long groupId, long languageId) {
		return (LanguageSpecificGroupDetails) getSession().createCriteria(LanguageSpecificGroupDetails.class)
				.add(Restrictions.eq("globlgroupId.id", groupId))
				.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageSpecificValueDetails> getGlobalValuesList(long paramId, long controlId, long langId) {
		List<LanguageSpecificValueDetails> lsvdList = null;
		List<Long> gvIds = null;
		try {
			gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.eq("globalParameter.id", paramId))
					.add(Restrictions.eq("controlType.id", controlId))
					.setProjection(Projections.property("globalValues.id")).list();
			
			lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.in("globalValId.id", gvIds))
					.add(Restrictions.eq("inlagId.id", langId)).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsvdList;
	}

	
	
}
