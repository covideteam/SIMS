package com.covideinfo.service;

import com.covideinfo.dto.GlobalParameterDetailsDto;

public interface StudyActivityTimePointsRetrivingSercive {

	GlobalParameterDetailsDto getStudyActivityTimePointsDtoDetails(Long studyId, String subjectNo, Long activityId, long languageId);
	

}
