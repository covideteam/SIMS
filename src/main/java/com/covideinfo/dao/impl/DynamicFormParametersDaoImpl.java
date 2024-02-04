package com.covideinfo.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dto.StudyDynamicFormParametersDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.ParameterControlTypeValues;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyGroupPeriodMaster;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.SubjectRandamization;
import com.covideinfo.model.TreatmentInfo;

@Repository("dynamicFormParametersDaoImpl")
public class DynamicFormParametersDaoImpl extends AbstractDao<Long, StudyMaster> {

	@SuppressWarnings("unchecked")
	public StudyDynamicFormParametersDto getStudyDesingActivityDetailsDtoDetails(Long studyId, Long activityId, Long langId, String subjectNo) {
		StudyDynamicFormParametersDto sdfpDto = null;
		long studyActivityId =0;
		List<LanguageSpecificGlobalParameterDetails> parametersList = null;
		Map<Long, Map<Long, List<LanguageSpecificValueDetails>>> controlTypeMap = new HashMap<>();
		StudySubjects ss = null;
		TreatmentInfo tinfo = null;
		StudyPeriodMaster spm = null;
		StatusMaster status =null;
		String radamizationCode = "";
		StudyActivities studyActivity = null;
		List<Long> gpIds = null;
		try {
			ss = (StudySubjects) getSession().createCriteria(StudySubjects.class)
					.add(Restrictions.eq("subjectNo", subjectNo)).uniqueResult();
			if(ss != null) {
				status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
						.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
				
				spm = (StudyPeriodMaster) getSession().createCriteria(StudyGroupPeriodMaster.class)
						.add(Restrictions.eq("studyId", studyId))
						.add(Restrictions.eq("studyGroup", ss.getGroupId()))
						.add(Restrictions.eq("periodStatus", status)).uniqueResult();
				if(spm != null) {
					radamizationCode = (String) getSession().createCriteria(SubjectRandamization.class)
							.add(Restrictions.eq("period", spm))
							.add(Restrictions.eq("subjectNo", ss.getSubjectNo()))
							.setProjection(Projections.property("radamizationCode")).uniqueResult();
					if(radamizationCode != null && !radamizationCode.equals("")) {
						
						tinfo = (TreatmentInfo) getSession().createCriteria(TreatmentInfo.class)
								.add(Restrictions.eq("randamizationCode", radamizationCode))
								.add(Restrictions.eq("study.id", studyId)).uniqueResult();
						if(tinfo != null) {
							studyActivity = (StudyActivities) getSession().createCriteria(StudyActivities.class)
									.add(Restrictions.eq("sm.id", studyId))
									.add(Restrictions.eq("studyPeriod", spm))
									.add(Restrictions.eq("treatment", tinfo))
									.add(Restrictions.eq("activityId.id", activityId)).uniqueResult();
							
							if(studyActivity != null) {
								studyActivityId = studyActivity.getId();
								gpIds = getSession().createCriteria(StudyActivityParameters.class)
										.add(Restrictions.eq("studyActivity", studyActivity))
										.setProjection(Projections.property("parameterId.id")).list();
							}
						}
					}
				}
			}
			if(gpIds != null && gpIds.size() > 0) {
				parametersList = getSession().createCriteria(LanguageSpecificGlobalParameterDetails.class)
						.add(Restrictions.eq("inlagId.id", langId))
						.add(Restrictions.in("parameterId.id", gpIds)).list();
				
				if(parametersList != null && parametersList.size() > 0) {
					for(LanguageSpecificGlobalParameterDetails lsgp : parametersList) {
						String controlType = lsgp.getParameterId().getContentType().getCode();
						Map<Long, List<LanguageSpecificValueDetails>> tempMap = null;
						if(controlType.equals("RB") || controlType.equals("SB") || controlType.equals("CB")){
							if(controlTypeMap.containsKey(lsgp.getParameterId().getContentType().getId())) {
								tempMap = controlTypeMap.get(lsgp.getParameterId().getContentType().getId());
								List<LanguageSpecificValueDetails> lsvdList = getLanguageSpecificGlobalValues(lsgp, langId);
								if(lsvdList != null && lsvdList.size() > 0) {
									tempMap.put(lsgp.getParameterId().getId(), lsvdList);
									controlTypeMap.put(lsgp.getParameterId().getContentType().getId(), tempMap);
								}
							}else {
								tempMap = new HashMap<>();
								List<LanguageSpecificValueDetails> lsvdList = getLanguageSpecificGlobalValues(lsgp, langId);
								if(lsvdList != null && lsvdList.size() > 0) {
									tempMap.put(lsgp.getParameterId().getId(), lsvdList);
									controlTypeMap.put(lsgp.getParameterId().getContentType().getId(), tempMap);
								}
							}
						}
					}
				}
			}
			sdfpDto = new StudyDynamicFormParametersDto();
			sdfpDto.setActivityId(activityId);
			sdfpDto.setControlTypeMap(controlTypeMap);
			sdfpDto.setParametersList(parametersList);
			sdfpDto.setStudyActivityId(studyActivityId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdfpDto;
	}

	@SuppressWarnings("unchecked")
	private List<LanguageSpecificValueDetails> getLanguageSpecificGlobalValues(
			LanguageSpecificGlobalParameterDetails lsgp, Long langId) {
		List<LanguageSpecificValueDetails> lsvdList = null;
		try {
			List<Long> gvIds = getSession().createCriteria(ParameterControlTypeValues.class)
					.add(Restrictions.eq("controlType", lsgp.getParameterId().getContentType()))
					.add(Restrictions.eq("globalParameter", lsgp.getParameterId()))
					.setProjection(Projections.property("globalValues.id")).list();
			
			if(gvIds != null && gvIds.size() > 0) {
				 lsvdList = getSession().createCriteria(LanguageSpecificValueDetails.class)
						.add(Restrictions.eq("inlagId.id", langId))
						.add(Restrictions.in("globalValId.id", gvIds)).list();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lsvdList;
	}
	
	

}
