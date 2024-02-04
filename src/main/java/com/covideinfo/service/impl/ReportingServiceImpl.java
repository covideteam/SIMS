package com.covideinfo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.covideinfo.dao.ReportingDao;
import com.covideinfo.dto.ReportingDto;
import com.covideinfo.model.GlobalActivity;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyActivityData;
import com.covideinfo.model.StudyActivityParameters;
import com.covideinfo.model.StudyExecutionActivityDataDetails;
import com.covideinfo.model.StudyGroup;
import com.covideinfo.model.StudySubjects;
import com.covideinfo.model.StudyVolunteerReporting;
import com.covideinfo.model.UserMaster;
import com.covideinfo.service.ReportingService;

@Service("reportingService")
public class ReportingServiceImpl implements ReportingService {
	
	@Autowired
	ReportingDao rportingDao;

	@Override
	public ReportingDto getReportingDtoDetails(Locale currentLocale) {
		return rportingDao.getReportingDtoDetails(currentLocale);
	}

	@Override
	public String savesaveReportingVolunteerData(Long projectId, StudyVolunteerReporting svr, String userName, Long genderId) {
		return rportingDao.savesaveReportingVolunteerData(projectId, svr, userName, genderId) ;
	}

	@Override
	public List<StudySubjects> getStudySubjectsWithGroupId(Long groupId) {
		return rportingDao.getStudySubjectsWithGroupId(groupId) ;
	}

	@Override
	public GlobalActivity getGlobalActivityWithName(String string) {
		return rportingDao.getGlobalActivityWithName(string) ;
	}

	@Override
	public StudyGroup getStudyGroupWithId(Long groupId) {
		return rportingDao.getStudyGroupWithId(groupId) ;
	}

	@Override
	public StudyActivities getStudyActivitiesWithStudyIdAndGlobalActivityId(GlobalActivity ga, StudyGroup sg) {
		return rportingDao.getStudyActivitiesWithStudyIdAndGlobalActivityId(ga,sg) ;
	}

	@Override
	public boolean saveAllSubjectActivityStart(List<StudySubjects> ss, StudyActivities sa,UserMaster um,StudyActivityParameters saplist) {
		boolean flag=false;
		
		try {
			List<StudyActivityData> sadList=new ArrayList<>();
			List<StudyExecutionActivityDataDetails> exeList=new ArrayList<>();
			
			for(StudySubjects sspojo:ss) {
				StudyActivityData sad=new StudyActivityData();
				StudyExecutionActivityDataDetails exepo=new StudyExecutionActivityDataDetails();
				sad.setCreatedBy(um.getUsername());
				sad.setCreatedOn(new Date());
				sad.setSutydActivity(sa);
				sad.setStatus("start");
				sad.setStdGoupPeriod(null);
				sad.setSubjectId(sspojo.getStdSubjectId());
				sad.setVolunteerId(sspojo.getReportingId());
				
					
					exepo.setComments("");
					exepo.setCreatedBy(um.getUsername());
					exepo.setCreatedOn(new Date());
					exepo.setDeviation(false);
					exepo.setGlobalValues(null);
					exepo.setSaData(sad);
					exepo.setSaParameter(saplist);
					exepo.setValue(""+new Date());
				
				
				sadList.add(sad);
				exeList.add(exepo);
			}
			
			flag=rportingDao.saveAllSubjectActivityStart(sadList,exeList);
		} catch (Exception e) {
			e.printStackTrace();
			return flag=false;
		}
		
		return flag;
	}

	@Override
	public StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesId(StudyActivities sa) {
		
		return rportingDao.getStudyActivityParametersWitgStudyActivitiesId(sa);
	}

	@Override
	public boolean saveAllSubjectActivityStop(List<StudySubjects> ss, StudyActivities sa, UserMaster um,String subjectNo,StudyActivityParameters saplist) {
		List<StudyExecutionActivityDataDetails> exeList=new ArrayList<>();
		List<StudyActivityData> sadli=new ArrayList<>();
		boolean flag=false;
		for(StudySubjects sspojo:ss) {
			StudyActivityData sad=rportingDao.getStudyActivityDataWithSubjectAndStudyActivities(sspojo,sa);
			StudyExecutionActivityDataDetails exepo=new StudyExecutionActivityDataDetails();
			exepo.setComments("");
			exepo.setCreatedBy(um.getUsername());
			exepo.setCreatedOn(new Date());
			if(subjectNo!="") {
				String [] sub=subjectNo.split(",");
				List<String> cids = new ArrayList<>();
				for (String s : sub)
					if (s.trim() != "")
						cids.add(s);
				if(cids.contains(sspojo.getSubjectNo())) {
					exepo.setDeviation(true);
				}else {
				exepo.setDeviation(false);
				}
			}else {
				exepo.setDeviation(false);
			}
			
			exepo.setGlobalValues(null);
			exepo.setSaData(sad);
			exepo.setSaParameter(saplist);
			exepo.setValue(""+new Date());
			sad.setReportStatus(true);
			sadli.add(sad);
		
		exeList.add(exepo);
		}
		flag=rportingDao.saveAllSubjectActivityStop(exeList,sadli);
		
		return flag;
	}

	@Override
	public StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesIdForStop(StudyActivities sa) {
		
		return rportingDao.getStudyActivityParametersWitgStudyActivitiesIdForStop(sa);
	}

	@Override
	public StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsCheckStart(GlobalActivity ga,
			StudySubjects fir) {
		return rportingDao.getStudyExecutionActivityDataDetailsCheckStart(ga,fir);
	}

	@Override
	public StudyActivityData getStudyActivityDataCheckAllSubjectActivity(GlobalActivity ga, StudyGroup sg,
			StudySubjects ssdata) {
		return rportingDao.getStudyActivityDataCheckAllSubjectActivity(ga,sg,ssdata);
	}

	

}
