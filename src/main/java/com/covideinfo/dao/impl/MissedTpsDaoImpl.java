package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.MissedTpsDao;
import com.covideinfo.dto.ActivityDetailsDto;
import com.covideinfo.dto.MissedTimePointsDetailsDto;
import com.covideinfo.dto.MissedTimePointsDto;
import com.covideinfo.dto.StudyMasterDto;
import com.covideinfo.dto.StudyPeriodsDto;
import com.covideinfo.dto.SubjectRandomizationDto;
import com.covideinfo.dto.VitalTimePointsDto;
import com.covideinfo.enums.StudyActivities;
import com.covideinfo.model.DoseTimePoints;
import com.covideinfo.model.DosingInfo;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalActivityDetails;
import com.covideinfo.model.MealsTimePoints;
import com.covideinfo.model.Projects;
import com.covideinfo.model.SampleTimePoints;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectDoseTimePoints;
import com.covideinfo.model.SubjectMealsTimePointsData;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.SubjectSampleCollectionTimePointsData;
import com.covideinfo.model.SubjectVitalTimePointsData;
import com.covideinfo.model.VitalTimePoints;

@Repository("missedTpsDao")
public class MissedTpsDaoImpl extends AbstractDao<Long, StudyMaster> implements MissedTpsDao {

	@SuppressWarnings("unchecked")
	@Override
	public MissedTimePointsDto getMissedTimePointsDtoDetails() {
		MissedTimePointsDto mtpDto = null;
		List<StudyMasterDto> smList = null;
		try {
			smList = getSession().createCriteria(StudyMaster.class)
					       .setProjection(Projections.projectionList()
					    		   .add(Projections.property("id"), "id")
					    		   .add(Projections.property("projectNo"), "projectNo"))
					       .setResultTransformer(Transformers.aliasToBean(StudyMasterDto.class)).list();
			mtpDto = new MissedTimePointsDto();
			 mtpDto.setSmList(smList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtpDto;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MissedTimePointsDetailsDto generateMissedTimepointsReport(Long studyId, Locale currentLocale) {
		MissedTimePointsDetailsDto mtpDto = null;
		List<StudyPeriodsDto> spmList = null;
		List<ActivityDetailsDto> actList = null;
		List<Long> actIds = null;
		List<String> actCodeList = new ArrayList<>();
		InternationalizaionLanguages inlg = null;
		String country = currentLocale.getCountry();
		List<MealsTimePoints> mealTpsList = null;
		List<VitalTimePointsDto> vitalList = null;
		List<SampleTimePoints> stpList = null;
		List<SubjectDoseTimePoints> sdtpList = null;
		List<SubjectMealsTimePointsData> smtpList = null;
		List<SubjectSampleCollectionTimePointsData> sctpList = null;
		List<SubjectVitalTimePointsData> svtpList = null;
		List<DoseTimePoints> doseList = null;
		List<Long> subjectIds = null;
		DosingInfo doseInfo = null;
		String projectName = "";
		Long projectId = null;
		List<Long> periodIds = null;
		List<StudySubjects> subjectsList = null;
		List<String> statusStr = new ArrayList<>();
		List<SubjectRandomizationDto> subradDtoList = null;
		List<SubjectDoseTimePoints> pwSubDoseList = null;
		Integer washoutPeriodDays=0;
		try {
			
			statusStr.add("Replace");
			statusStr.add("active");
			
			actCodeList.add(StudyActivities.MealsCollection.toString());
			actCodeList.add(StudyActivities.SampleCollection.toString());
			actCodeList.add(StudyActivities.DosingCollection.toString());
			actCodeList.add(StudyActivities.StudyExecutionVitals.toString());
			
			washoutPeriodDays = (Integer) getSession().createCriteria(StudyMaster.class)
					               .add(Restrictions.eq("id", studyId))
					               .setProjection(Projections.property("washoutPeriod")).uniqueResult();
			
			spmList =  getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study.id", studyId))
				       .setProjection(Projections.projectionList()
				    		   .add(Projections.property("id"), "id")
				    		   .add(Projections.property("periodName"), "periodName")
				    		   .add(Projections.property("periodNo"), "periodNo"))
				       .setResultTransformer(Transformers.aliasToBean(StudyPeriodsDto.class)).list();
		 
			actIds = getSession().createCriteria(GlobalActivity.class)
					     .add(Restrictions.in("activityCode", actCodeList))
					     .setProjection(Projections.property("id")).list();
			
			if(actIds != null && actIds.size() > 0) {
				inlg = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
						.add(Restrictions.eq("countryCode", country)).uniqueResult();
				
				actList = getSession().createCriteria(LanguageSpecificGlobalActivityDetails.class)
						.add(Restrictions.in("globalActivity.id", actIds))
						.add(Restrictions.eq("inlagId.id", inlg.getId()))
						.createAlias("globalActivity", "ga")
						 .setProjection(Projections.projectionList()
					    		   .add(Projections.property("ga.id"), "actId")
					    		   .add(Projections.property("name"), "actName")
					    		   .add(Projections.property("ga.activityCode"), "activityCode"))
						   .setResultTransformer(Transformers.aliasToBean(ActivityDetailsDto.class)).list();
			}
			mealTpsList =  getSession().createCriteria(MealsTimePoints.class)
							.add(Restrictions.eq("study.id", studyId))
					        .setProjection(Projections.distinct(Projections.projectionList()
					        		.add(Projections.property("timePoint"), "timePoint")
					        		.add(Projections.property("sign"), "sign")))
					        .setResultTransformer(Transformers.aliasToBean(MealsTimePoints.class)).list();
		
			vitalList  =  getSession().createCriteria(VitalTimePoints.class)
							.add(Restrictions.eq("study.id", studyId))
							.createAlias("vitalPosition", "vp")
							.createAlias("orthostaticPosition", "ortho", JoinType.LEFT_OUTER_JOIN)
							.setProjection(Projections.distinct(Projections.projectionList()
			        		  .add(Projections.property("sign"), "sign")
				        		.add(Projections.property("timePoint"), "timePoint")
								.add(Projections.property("orthostatic"), "orthostatic")
								.add(Projections.property("vp.fieldValue"), "vitalPosition")
								.add(Projections.property("ortho.fieldValue"), "orthostaticPosition")))
							 .setResultTransformer(Transformers.aliasToBean(VitalTimePointsDto.class)).list();

			stpList = getSession().createCriteria(SampleTimePoints.class)
					     .add(Restrictions.eq("study.id", studyId))
					     .setProjection(Projections.distinct(Projections.projectionList()
					        		.add(Projections.property("timePoint"), "timePoint")
					        		.add(Projections.property("sign"), "sign")
					        		.add(Projections.property("noOfVacutainer"), "noOfVacutainer")
					        		.add(Projections.property("vacutainerNo"), "vacutainerNo")))
					        .setResultTransformer(Transformers.aliasToBean(SampleTimePoints.class)).list();
			
			doseList = getSession().createCriteria(DoseTimePoints.class)
					       .add(Restrictions.eq("study.id", studyId))
					       .setProjection(Projections.projectionList()
					        		  .add(Projections.property("treatmentInfo"), "treatmentInfo"))
					       .setProjection(Projections.distinct(Projections.projectionList()
					    		   .add(Projections.property("timePoint"), "timePoint")))
					    		   .setResultTransformer(Transformers.aliasToBean(DoseTimePoints.class)).list();
			
			subjectIds = getSession().createCriteria(StudySubjects.class)
					        .add(Restrictions.eq("study.id", studyId))
					        .setProjection(Projections.property("id")).list();
			
			if(subjectIds != null && subjectIds.size() > 0) {
				sdtpList = getSession().createCriteria(SubjectDoseTimePoints.class)
						         .add(Restrictions.in("studySubjects.id", subjectIds)).list();
				
				smtpList = getSession().createCriteria(SubjectMealsTimePointsData.class)
						       .add(Restrictions.in("subject.id", subjectIds)).list();
				
				sctpList = getSession().createCriteria(SubjectSampleCollectionTimePointsData.class)
						        .add(Restrictions.in("subject.id", subjectIds)).list();
				
				svtpList = getSession().createCriteria(SubjectVitalTimePointsData.class)
						        .add(Restrictions.in("subject.id", subjectIds)).list();
			}
			projectName = (String) getSession().createCriteria(StudyMaster.class)
					         .add(Restrictions.eq("id", studyId))
					         .setProjection(Projections.property("projectNo")).uniqueResult();
			
			projectId = (Long) getSession().createCriteria(Projects.class)
					       .add(Restrictions.eq("projectNo", projectName))
					       .setProjection(Projections.property("projectId")).uniqueResult();
			if(projectId != null) {
				doseInfo = (DosingInfo) getSession().createCriteria(DosingInfo.class)
					       .add(Restrictions.eq("projects.id", projectId)).uniqueResult();
			}
			periodIds = getSession().createCriteria(StudyPeriodMaster.class)
					       .add(Restrictions.eq("study.id", studyId))
					       .setProjection(Projections.property("id")).list();
			
			
			subjectsList = getSession().createCriteria(StudySubjects.class)
					                   .add(Restrictions.eq("study.id", studyId))
					                   .add(Restrictions.in("subjectStatus", statusStr)).list();
			if(periodIds.size() > 0) {
				subradDtoList = getSession().createCriteria(SubjectRandamization.class)
			              .add(Restrictions.in("period.id", periodIds))
			              .createAlias("period", "p")
			              .createAlias("treatmentInfo", "tr")
			              .setProjection(Projections.projectionList()
			            		  .add(Projections.property("subjectNo"), "subjectNo")
			            		  .add(Projections.property("p.id"), "periodId")
			            		  .add(Projections.property("tr.id"), "treatmentId"))
			              .setResultTransformer(Transformers.aliasToBean(SubjectRandomizationDto.class)).list();
				
				pwSubDoseList = getSession().createCriteria(SubjectDoseTimePoints.class)
			               .add(Restrictions.in("period.id", periodIds))
			               .setProjection(Projections.projectionList()
			            		  .add(Projections.groupProperty("period"), "period")
			                      .add(Projections.max("actualTime"), "actualTime"))
			               .setResultTransformer(Transformers.aliasToBean(SubjectDoseTimePoints.class)).list();
			}
			
			mtpDto = new MissedTimePointsDetailsDto();
			mtpDto.setActList(actList);
			mtpDto.setSpmList(spmList);
			mtpDto.setMealTpsList(mealTpsList);
			mtpDto.setStpList(stpList);
			mtpDto.setVitalList(vitalList);
			mtpDto.setDoseList(doseList);
			mtpDto.setSdtpList(sdtpList);
			mtpDto.setSmtpList(smtpList);
			mtpDto.setSctpList(sctpList);
			mtpDto.setSvtpList(svtpList);
			mtpDto.setDoseInfo(doseInfo);
			mtpDto.setPeriodIds(periodIds);
			mtpDto.setSubjectsList(subjectsList);
			mtpDto.setSubradDtoList(subradDtoList);
			mtpDto.setPwSubDoseList(pwSubDoseList);
			mtpDto.setWashoutPeriodDays(washoutPeriodDays);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mtpDto;
	}

}
