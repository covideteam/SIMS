package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.StudyExeCutionDao;
import com.covideinfo.dto.StudyExecutionDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

@Repository("studyExeCutionDao")
public class StudyExeCutionDaoImpl extends AbstractDao<Long, StudyMaster> implements StudyExeCutionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyMaster> getStudyMasterList() {
		return getSession().createCriteria(StudyMaster.class).list();
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public StudyExecutionDto getVolunteerList(Long studyId, Long activityId, String param, Long languageId,Boolean isSubjectScanned) {
		StudyExecutionDto sedto = null;
		List<StudyVolunteerReporting> svrList = new ArrayList<>();
		LanguageSpecificGlobalActivityDetails lsga = null;
		StatusMaster status = null;
		List<Long> svrIds = null;
		List<StudyVolunteerReporting> svrTempList = null;
		List<StudyPeriodMaster> spmList = null;
		List<StudySubjectPeriods> sspList = null;
		List<Long> subjectIds = null;
		List<String> subjectNos = null;
		List<SubjectRandamization> srzList = new ArrayList<>();
		Long treatementOnId = null;
		
		try {
			
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					       .add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			if(isSubjectScanned) {// Condition to execute in case of subject bar code is scanned.
				svrList = getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.eq("subjectNo", param))
						.add(Restrictions.eq("study.id", studyId))
						.setProjection(Projections.property("reportingId")).list();
			}
			else {
				svrList = getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.ilike("subjectNo", param, MatchMode.ANYWHERE))
						.add(Restrictions.eq("study.id", studyId))
						.setProjection(Projections.property("reportingId")).list();
				
				svrIds = getSession().createCriteria(StudyVolunteerReporting.class)
						.add(Restrictions.eq("status", status))
						.add(Restrictions.ilike("volunteerId", param, MatchMode.ANYWHERE))
						.add(Restrictions.eq("sm.id", studyId))
						.setProjection(Projections.property("id")).list();
				
				if(svrIds != null && svrIds.size() > 0) {
					svrTempList = getSession().createCriteria(StudyVolunteerReporting.class)
										.add(Restrictions.in("id", svrIds)).list();
				}
				
				if(svrTempList != null) {
					svrList.addAll(svrTempList);
				}
			}
			
			
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					        .add(Restrictions.eq("study.id", studyId)).list();
			
			subjectIds = getSession().createCriteria(StudySubjects.class)
					       .add(Restrictions.eq("study.id", studyId))
					       .setProjection(Projections.property("id")).list();
			
			subjectNos = getSession().createCriteria(StudySubjects.class)
				       .add(Restrictions.eq("study.id", studyId))
				       .setProjection(Projections.property("subjectNo")).list();
			
			if(subjectIds != null && subjectIds.size() > 0) {
				sspList = getSession().createCriteria(StudySubjectPeriods.class)
						     .add(Restrictions.eq("status", status))
						     .add(Restrictions.in("subject.id", subjectIds)).list();
			}
			if((subjectNos != null && subjectNos.size() > 0) && (spmList != null && spmList.size() > 0)) {
				for(StudyPeriodMaster spm : spmList) {
					List<SubjectRandamization> srzlist = getSession().createCriteria(SubjectRandamization.class)
						    .add(Restrictions.eq("period", spm))
						    .add(Restrictions.in("subjectNo", subjectNos)).list();
					if(srzlist != null && srzlist.size() > 0)
						srzList.addAll(srzlist);
				}
				
			}
			treatementOnId = (Long) getSession().createCriteria(TreatmentInfo.class)
					           .add(Restrictions.eq("study.id", studyId))
					           .setProjection(Projections.min("id")).uniqueResult();
			
			lsga = (LanguageSpecificGlobalActivityDetails) getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
					.add(Restrictions.eq("globalActivity.id", activityId))
					.add(Restrictions.eq("inlagId.id", languageId)).uniqueResult();
			
			sedto = new StudyExecutionDto();
			sedto.setLsga(lsga);
			sedto.setSvrList(svrList);
			sedto.setSpmList(spmList);
			sedto.setSrzList(srzList);
			sedto.setSspList(sspList);
			sedto.setTreatmentOneId(treatementOnId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sedto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudyActivities> getStudyActivityList(Long studyId) {
		List<StudyActivities> finalstudyActivities  = new ArrayList<>();
		List<Long> saIds = new ArrayList<>();
		List<StudyActivities> studyActivities  = null;
		List<String> actCodesList = new ArrayList<>();
	    List<GlobalActivity> gaList = null;
		try {
			actCodesList.add(com.covideinfo.enums.StudyActivities.DosingCollection.toString());
//			actCodesList.add(com.covideinfo.enums.StudyActivities.BAGGAGERETURN.toString());
			
			actCodesList.add(com.covideinfo.enums.StudyActivities.MealsCollection.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.SampleCollection.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.Centrifuge.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.SampleSeparation.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.SampleStorage.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.SampleSegregation.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.StudyExecutionVitals.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.VialRack.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.Reporting.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.SubjectWiseSamplesContainter.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.Shipment.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.StudyWithDraw.toString());
			actCodesList.add(com.covideinfo.enums.StudyActivities.Recannulation.toString());
			

			gaList = getSession().createCriteria(GlobalActivity.class)
					.add(Restrictions.in("activityCode", actCodesList)).list();
			if(gaList != null && gaList.size() > 0) {
				for(GlobalActivity ga : gaList) {
					if(ga.getActivityCode().equals("Reporting"))
					System.out.println("Reporting");
					StudyActivities sa = new StudyActivities();
					sa.setActivityId(ga);
					finalstudyActivities.add(sa);
				}
			}
			studyActivities  = getSession().createCriteria(StudyActivities.class)
						.add(Restrictions.eq("sm.id", studyId)).list();
			if(studyActivities.size() > 0) {
				for(StudyActivities sa : studyActivities) {
					if(sa.getActivityId() != null) {
						if(!saIds.contains(sa.getActivityId().getId())) {
							saIds.add(sa.getActivityId().getId());
							finalstudyActivities.add(sa);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(StudyActivities sa : finalstudyActivities) {
			System.out.println("Activity Name : "+ sa.getActivityId().getName());
		}
		return finalstudyActivities;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<com.covideinfo.model.UserWiseStudiesAsignMaster> UserWiseStudiesAsignMaster(Long userId) {
		return getSession().createCriteria(UserWiseStudiesAsignMaster.class)
				.add(Restrictions.eq("userId.id", userId))
				.add(Restrictions.eq("status", true))
				.list();
	}
}
