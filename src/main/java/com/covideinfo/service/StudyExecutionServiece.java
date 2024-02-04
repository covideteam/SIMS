package com.covideinfo.service;

import java.util.List;

import com.covideinfo.dto.ActivityDto;
import com.covideinfo.dto.VolunteerDto;
import com.covideinfo.model.StudyMaster;

public interface StudyExecutionServiece {

	List<StudyMaster> getStudyMasterList(Long role);
	
	List<VolunteerDto> getVolunteerList(Long studyId, Long activityId, String param, Long languageId,Boolean isSubjectScanned);

	ActivityDto getActivityDto(Long studyId, Long role);

	

}
