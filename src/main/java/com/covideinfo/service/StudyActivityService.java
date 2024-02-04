package com.covideinfo.service;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.MessageSource;

import com.covideinfo.dto.GlobalParameterDetailsDto;
import com.covideinfo.dto.GlobalparameterFromDto;
import com.covideinfo.dto.MessageDto;
import com.covideinfo.dto.StudyActivitiesSavingDto;
import com.covideinfo.dto.StudyDesingActivityDetailsDto;
import com.covideinfo.model.ProjectsDetails;
import com.covideinfo.model.StudyMaster;
import com.covideinfo.model.SubjectActivityData;

public interface StudyActivityService {

	List<GlobalParameterDetailsDto> getStudyActivityDetails(Long activityId, Long languageId, Long studyId);

	StudyDesingActivityDetailsDto getStudyDesingActivityDetailsDtoDetails(Long activityId, Long languageId);

	MessageDto saveStudyActivityFromsData(SubjectActivityData sad, MessageSource messageSource, Locale currentLocale, String userName,Long languageId, String dateFormat, String dateStrWithTime);

	StudyActivitiesSavingDto saveStudyDesignActivityDetails(StudyMaster study, Map<Integer, List<ProjectsDetails>> rescompMap,
			Map<Integer, List<ProjectsDetails>> inclusionMap, Map<Integer, List<ProjectsDetails>> exclusionMap, Map<Long, List<Long>> map)throws Exception;

	GlobalparameterFromDto globalparameterFromDtoerFromDto(Long activityId, Long languageId, Long studyId,
			Locale currentLocale);

	StudyDesingActivityDetailsDto getActivityParamters(Long activityId, Long languageId) ;

	GlobalparameterFromDto getLanguageSpecificGlobalParameters(List<Long> doseIds, Long languageId, long actId);

}
