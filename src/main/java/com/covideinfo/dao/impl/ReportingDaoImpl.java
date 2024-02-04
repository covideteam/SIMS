package com.covideinfo.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.covideinfo.abstractdao.AbstractDao;
import com.covideinfo.dao.ReportingDao;
import com.covideinfo.dto.ReportingDto;
import com.covideinfo.enums.StudyStatus;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.GlobalValues;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StatusMaster;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.StudyPeriodMaster;
import com.covideinfo.model.StudySubjectPeriods;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;

@Repository("reportingDao")
public class ReportingDaoImpl extends AbstractDao<Long, StudyMaster> implements ReportingDao {

	@SuppressWarnings("unchecked")
	@Override
	public ReportingDto getReportingDtoDetails(Locale currentLocale) {
		ReportingDto rdto = null;
		List<LanguageSpecificValueDetails> lspvList = null;
		List<Long> gvIds = null;
		List<String> strList = new ArrayList<>();
		InternationalizaionLanguages inalg = null;
		try {
			strList.add("Male");
			strList.add("Female");
			inalg = (InternationalizaionLanguages) getSession().createCriteria(InternationalizaionLanguages.class)
					.add(Restrictions.eq("countryCode", currentLocale.getCountry())).uniqueResult();
			
			gvIds = getSession().createCriteria(GlobalValues.class)
					.add(Restrictions.in("name", strList))
					.setProjection(Projections.property("id")).list();
			if(gvIds.size() > 0) {
				lspvList = getSession().createCriteria(LanguageSpecificValueDetails.class)
						.add(Restrictions.in("globalValId.id", gvIds))
						.add(Restrictions.eq("inlagId", inalg)).list();
			}
			rdto = new ReportingDto();
			rdto.setLsvList(lspvList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rdto;
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public String savesaveReportingVolunteerData(Long projectId, StudyVolunteerReporting svr, String userName, Long genderId) {
		String result ="Failed";
		StudyMaster sm = null;
		long svrNo = 0;
		LanguageSpecificValueDetails lsv = null;
		StatusMaster status = null;
		List<StudyPeriodMaster> spmList = null;
		Map<Integer, StudyPeriodMaster> spmMap = new HashMap<>();
		StudyVolunteerReporting svrPojo = null;
		StudySubjects subjects = null;
		StatusMaster completeStatus = null;
		List<StudyVolunteerReporting> svrPojoList = null;
		Map<String, Long> volIdsMap = new HashMap<>();
		try {
			sm = (StudyMaster) getSession().createCriteria(StudyMaster.class)
					.add(Restrictions.eq("id", projectId)).uniqueResult();
			if(sm != null) {
				svrPojoList = getSession().createCriteria(StudyVolunteerReporting.class)
						.add(Restrictions.eq("sm", sm)).list();
				if(svrPojoList != null && svrPojoList.size() > 0) {
					for(StudyVolunteerReporting vol : svrPojoList) {
						if(vol.getSubjectNo() != null && !vol.getSubjectNo().equals(""))
							volIdsMap.put(vol.getVolunteerId()+"("+vol.getSubjectNo()+")", vol.getId());
						else volIdsMap.put(vol.getVolunteerId(), vol.getId());
					}
				}
			}
			
			status = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					.add(Restrictions.eq("statusCode", StudyStatus.ACTIVE.toString())).uniqueResult();
			
			completeStatus = (StatusMaster) getSession().createCriteria(StatusMaster.class)
					             .add(Restrictions.eq("statusCode", StudyStatus.COMPLETED.toString())).uniqueResult();
			
			spmList = getSession().createCriteria(StudyPeriodMaster.class)
					.add(Restrictions.eq("study", sm)).list();
			
			lsv = (LanguageSpecificValueDetails) getSession().createCriteria(LanguageSpecificValueDetails.class)
					.add(Restrictions.eq("id", genderId)).uniqueResult();
			
			if(spmList != null && spmList.size() > 0) {
				for(StudyPeriodMaster spm : spmList) {
					spmMap.put(spm.getPeriodNo(), spm);
				}
			}
			svrPojo = (StudyVolunteerReporting) getSession().createCriteria(StudyVolunteerReporting.class)
					.add(Restrictions.eq("id", volIdsMap.get(svr.getVolunteerId())))
					.add(Restrictions.eq("sm.id", projectId)).uniqueResult();
			
			if(svrPojo != null) {
				int periodNo = 0;
				StudyPeriodMaster sp = null;
			    subjects = (StudySubjects) getSession().createCriteria(StudySubjects.class)
			    		        .add(Restrictions.eq("reportingId", svrPojo)).uniqueResult();
				if(subjects != null) {
					boolean sspFlag = false;
					boolean sspNewFlag = false;
					periodNo = periodNo + svrPojo.getPeriod().getPeriodNo();
					periodNo++;
					sp = spmMap.get(periodNo);
				   if(sp != null) {
						svrPojo.setPeriod(sp);
						svrPojo.setUpdatedBy(userName);
						svrPojo.setComments(sp.getPeriodName()+" Reporting");
						svrPojo.setUpdatedOn(new Date());
						getSession().merge(svrPojo);
						
						StudySubjectPeriods ssp = (StudySubjectPeriods) getSession().createCriteria(StudySubjectPeriods.class)
								                   .add(Restrictions.eq("subject", subjects))
								                   .add(Restrictions.eq("periodId", spmMap.get(periodNo-1))).uniqueResult();
						
						StudySubjectPeriods sspNew = (StudySubjectPeriods) getSession().createCriteria(StudySubjectPeriods.class)
				                   .add(Restrictions.eq("subject", subjects))
				                   .add(Restrictions.eq("periodId", sp)).uniqueResult();
						
						if(ssp != null) {
							ssp.setUpdatedBy(userName);
							ssp.setStatus(completeStatus);
							ssp.setUpdatedOn(new Date());
							ssp.setUpdateReason("Volunteer Before Period Completed.");
							getSession().merge(ssp);
							sspFlag = true;
						}
						if(sspNew != null) {
							sspNew.setUpdatedBy(userName);
							sspNew.setStatus(status);
							sspNew.setUpdatedOn(new Date());
							sspNew.setUpdateReason("Volunteer Reported to Next Period");
							getSession().merge(sspNew);
							sspNewFlag = true;
						}
						if(sspFlag && sspNewFlag)
							result ="success";
					}else result = "NoPeriods";
				}else result ="exists";
			}else {
				if(svrPojo == null) {
					svr.setCreatedBy(userName);
					svr.setCreatedOn(new Date());
					svr.setPeriod(spmMap.get(1));
					svr.setSm(sm);
					svr.setGenderId(lsv);
					svr.setStatus(status);
					svrNo = (long) getSession().save(svr);
					if(svrNo > 0)
						result ="success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StudySubjects> getStudySubjectsWithGroupId(Long groupId) {
		 List<StudySubjects> sslist=null;
		 sslist=getSession().createCriteria(StudySubjects.class).add(Restrictions.eq("groupId.id", groupId)).list();
		
	return sslist;
	}

	@Override
	public GlobalActivity getGlobalActivityWithName(String string) {
		GlobalActivity ga=null;
		ga=(GlobalActivity) getSession().createCriteria(GlobalActivity.class)
				.add(Restrictions.eq("name", string)).uniqueResult();
		return ga;
	}

	@Override
	public StudyGroup getStudyGroupWithId(Long groupId) {
		StudyGroup sg=null;
		sg=(StudyGroup) getSession().createCriteria(StudyGroup.class)
				.add(Restrictions.eq("id", groupId)).uniqueResult();
		return sg;
	}

	@Override
	public StudyActivities getStudyActivitiesWithStudyIdAndGlobalActivityId(GlobalActivity ga, StudyGroup sg) {
		StudyActivities sa=null;
		sa=(StudyActivities) getSession().createCriteria(StudyActivities.class)
				.add(Restrictions.eq("sm", sg.getStudy()))
				.add(Restrictions.eq("activityId", ga)).uniqueResult();
		return sa;
	}

	@Override
	public StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesId(StudyActivities sa) {
		StudyActivityParameters sappojo=null;
		sappojo=(StudyActivityParameters) getSession().createCriteria(StudyActivityParameters.class)
				.add(Restrictions.eq("studyActivity", sa))
				.add(Restrictions.eq("parameterId.parameterName", "startDate"))
				.uniqueResult();
		return sappojo;
	}

	@Override
	public boolean saveAllSubjectActivityStart(List<StudyActivityData> sadList,
			List<StudyExecutionActivityDataDetails> exeList) {
		boolean flag=false;
		try {
			for(StudyActivityData avdp:sadList){
				getSession().save(avdp);
			}
			for(StudyExecutionActivityDataDetails exc:exeList){
				getSession().save(exc);
			}
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesIdForStop(StudyActivities sa) {
		StudyActivityParameters sappojo=null;
		sappojo=(StudyActivityParameters) getSession().createCriteria(StudyActivityParameters.class)
				.add(Restrictions.eq("studyActivity", sa))
				.add(Restrictions.eq("parameterId.parameterName", "stopDate"))
				.uniqueResult();
		return sappojo;
	}

	@Override
	public StudyActivityData getStudyActivityDataWithSubjectAndStudyActivities(StudySubjects sspojo,
			StudyActivities sa) {
		StudyActivityData sapojo=null;
		sapojo=(StudyActivityData) getSession().createCriteria(StudyActivityData.class)
				.add(Restrictions.eq("sutydActivity", sa))
				.add(Restrictions.eq("subjectId", sspojo))
				.uniqueResult();
		return sapojo;
	}

	@Override
	public boolean saveAllSubjectActivityStop(List<StudyExecutionActivityDataDetails> exeList,List<StudyActivityData> sadli) {
		boolean flag=false;
		try {
			for(StudyExecutionActivityDataDetails add:exeList){
				getSession().save(add);
			}
			for(StudyActivityData add:sadli){
				getSession().save(add);
			}
			
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsCheckStart(GlobalActivity ga,
			StudySubjects fir) {
		return null;
	}

	@Override
	public StudyActivityData getStudyActivityDataCheckAllSubjectActivity(GlobalActivity ga, StudyGroup sg,
			StudySubjects ssdata) {
		/*StudyActivityData sad=null;
		sad= getSession().createCriteria(StudyActivityData.class)
				.add(Restrictions.eq("subjectId", ssdata))
				.add(Restrictions.eq("sutydActivity.activityId", ga))
				.add(Restrictions.eq("sutydActivity.sm", sg.getStudy())
				.add(Restrictions.eq("reportStatus", false))
				.uniqueResult();
				
		return sad;*/
		StudyActivityData sa=null;
		sa=(StudyActivityData) getSession().createCriteria(StudyActivityData.class)
				.add(Restrictions.eq("subjectId", ssdata))
				.add(Restrictions.eq("sutydActivity.activityId", ga))
				.add(Restrictions.eq("sutydActivity.sm", sg.getStudy()))
				.add(Restrictions.eq("reportStatus", false))
				.add(Restrictions.eq("activityId", ga)).uniqueResult();
		return sa;
	}

	

}
