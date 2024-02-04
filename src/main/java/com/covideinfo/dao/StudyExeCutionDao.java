package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.StudyExecutionDto;
import com.covideinfo.model.StudyActivities;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.UserWiseStudiesAsignMaster;

public interface StudyExeCutionDao {

	List<StudyMaster> getStudyMasterList();
	
	StudyExecutionDto getVolunteerList(Long studyId, Long activityId, String param, Long languageId,Boolean isSubjectScanned);

	List<StudyActivities> getStudyActivityList(Long studyId);

	List<com.covideinfo.model.UserWiseStudiesAsignMaster> UserWiseStudiesAsignMaster(Long userId);

	

}
