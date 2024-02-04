package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

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

public interface ReportingService {

	String savesaveReportingVolunteerData(Long projectId, StudyVolunteerReporting svr, String userName, Long genderId);

	ReportingDto getReportingDtoDetails(Locale currentLocale);

	List<StudySubjects> getStudySubjectsWithGroupId(Long groupId);

	GlobalActivity getGlobalActivityWithName(String string);

	StudyGroup getStudyGroupWithId(Long groupId);

	StudyActivities getStudyActivitiesWithStudyIdAndGlobalActivityId(GlobalActivity ga, StudyGroup sg);

	boolean saveAllSubjectActivityStart(List<StudySubjects> ss, StudyActivities sa, UserMaster um, StudyActivityParameters saplist);

	StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesId(StudyActivities sa);

	boolean saveAllSubjectActivityStop(List<StudySubjects> ss, StudyActivities sa, UserMaster um, String subjectNo, StudyActivityParameters saplist);

	StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesIdForStop(StudyActivities sa);

	StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsCheckStart(GlobalActivity ga,
			StudySubjects fir);

	StudyActivityData getStudyActivityDataCheckAllSubjectActivity(GlobalActivity ga, StudyGroup sg, StudySubjects ssdata);

}
