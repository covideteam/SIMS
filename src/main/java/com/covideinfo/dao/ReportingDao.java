package com.covideinfo.dao;

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

public interface ReportingDao {

	ReportingDto getReportingDtoDetails(Locale currentLocale);

	String savesaveReportingVolunteerData(Long projectId, StudyVolunteerReporting svr, String userName, Long genderId);

	List<StudySubjects> getStudySubjectsWithGroupId(Long groupId);

	GlobalActivity getGlobalActivityWithName(String string);

	StudyGroup getStudyGroupWithId(Long groupId);

	StudyActivities getStudyActivitiesWithStudyIdAndGlobalActivityId(GlobalActivity ga, StudyGroup sg);

	StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesId(StudyActivities sa);

	boolean saveAllSubjectActivityStart(List<StudyActivityData> sadList,
			List<StudyExecutionActivityDataDetails> exeList);

	StudyActivityParameters getStudyActivityParametersWitgStudyActivitiesIdForStop(StudyActivities sa);

	StudyActivityData getStudyActivityDataWithSubjectAndStudyActivities(StudySubjects sspojo, StudyActivities sa);

	boolean saveAllSubjectActivityStop(List<StudyExecutionActivityDataDetails> exeList, List<StudyActivityData> sadli);

	StudyExecutionActivityDataDetails getStudyExecutionActivityDataDetailsCheckStart(GlobalActivity ga,
			StudySubjects fir);

	StudyActivityData getStudyActivityDataCheckAllSubjectActivity(GlobalActivity ga, StudyGroup sg,
			StudySubjects ssdata);

	

	

}
