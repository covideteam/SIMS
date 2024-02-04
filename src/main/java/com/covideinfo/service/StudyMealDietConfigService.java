package com.covideinfo.service;

import java.util.List;

import com.covideinfo.dto.StudyMealsDietConfiguraionDto;

public interface StudyMealDietConfigService {

	StudyMealsDietConfiguraionDto getStudyMealsDietConfiguraionDtoDetails();

	StudyMealsDietConfiguraionDto getStudyRelatedMealsConfigurationDetails(Long studyId);

	boolean saveStudyMealDietCofigurationData(Long projectId, List<String> mealdietList, String userName);

}
