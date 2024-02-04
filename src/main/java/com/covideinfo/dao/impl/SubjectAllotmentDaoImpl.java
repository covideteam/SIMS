package com.covideinfo.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.SubjectAllotmentDao;
import com.covideinfo.dto.SubjectAllotmentDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.CustomActivityParameters;
import com.covideinfo.model.GlobalParameter;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.SubjectActivityData;

@Repository("inclusionExclusionDao")
public class SubjectAllotmentDaoImpl extends AbstractDao<Long, StudySubjects> implements SubjectAllotmentDao {

	
	@SuppressWarnings("unchecked")
	@Override
	public List<GlobalParameter> getGlobalparametersList(List<Long> parmIdsList) {
		return getSession().createCriteria(GlobalParameter.class)
				.add(Restrictions.in("id", parmIdsList)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String subjectAllotment(SubjectActivityData sad, String userName,  String subjectNo) {
		String result ="Failed";
		StudySubjects ss = null;
		StudyVolunteerReporting svr = null;
		StudyGroup studyGroup = null;
		List<StudyPeriodMaster> spmList = null;
		long ssNo = 0;
		try {
			svr = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
					.add(Restrictions.eq("id", sad.getVolId())).uniqueResult();
			if(svr != null) {
				Long maxId = (Long) getSession().createCriteria(StudyGroup.class)
						.add(Restrictions.eq("study", svr.getSm()))
						.setProjection(Projections.max("id")).uniqueResult();
				if(maxId != null) {
					studyGroup = (StudyGroup) getSession().createCriteria(StudyGroup.class)
								.add(Restrictions.eq("id", maxId)).uniqueResult();
				}
				
				ss = new StudySubjects();
				ss.setCreatedBy(userName);
				ss.setCreatedOn(new Date());
				ss.setReportingId(svr);
				ss.setSubjectNo(subjectNo);
				ss.setStudy(svr.getSm());
				ss.setGroupId(studyGroup);
				ssNo = (long) getSession().save(ss);
				if(ssNo > 0) {
					spmList = getSession().createCriteria(StudyPeriodMaster.class)
							.add(Restrictions.eq("study", ss.getStudy())).list();
					if(spmList != null && spmList.size() > 0) {
						for(StudyPeriodMaster spm : spmList) {
							StudySubjectPeriods ssp = new StudySubjectPeriods();
							ssp.setCreatedBy(userName);
							ssp.setCreatedOn(new Date());
							ssp.setPeriodId(spm);
							ssp.setSubject(ss);
							StatusMaster status = null;
							if(spm.getPeriodNo() == 1) {
								status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
										.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
							}else {
								status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
										.add(Restrictions.eq("statusCode", StudyStatus.NEW.toString())).uniqueResult();
							}
							ssp.setStatus(status);
							long sspNo = (long) getSession().save(ssp);
							if(sspNo > 0)
								result ="success";
						}
					}
				}
					
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result ="Failed";
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SubjectAllotmentDto getSubjectAllotmentDtoDetails(Long studyId) {
		SubjectAllotmentDto sadto = null;
		List<String> subjList = null;
		List<Long> stdGroupIds = null;
		int totalSubjects = 0;
		StudyMaster sm = null;
		try {
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", studyId)).uniqueResult();
			
			stdGroupIds = getSession().createCriteria(StudyGroup.class)
					.add(Restrictions.eq("study.id", studyId))
					.setProjection(Projections.property("id")).list();
			if(stdGroupIds != null) {
				subjList = getSession().createCriteria(StudySubjects.class)
						.add(Restrictions.in("groupId.id", stdGroupIds))
						.setProjection(Projections.property("subjectNo")).list();
			}
			totalSubjects = sm.getNoOfStandBySubjects()+sm.getNoOfSubjects();
			
			sadto = new SubjectAllotmentDto();
			sadto.setSubjList(subjList);
			sadto.setTotalSubjects(totalSubjects);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sadto;
	}

	@Override
	public CustomActivityParameters getCustomActivityParametersRecord(Long activityId) {
		return (CustomActivityParameters) getSession().createCriteria(CustomActivityParameters.class)
				.add(Restrictions.eq("activity.id", activityId)).uniqueResult();
	}

}
