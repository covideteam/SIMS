package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.StudyMealTimePointDietDto;
import com.covideinfo.dto.StudyMealsDietConfiguraionDetailsDto;

public interface StudyMealDietConfigDao {

	StudyMealsDietConfiguraionDetailsDto getStudyMealsDietConfiguraionDtoDetails();

	StudyMealsDietConfiguraionDetailsDto getStudyRelatedMealsConfigurationDetails(Long studyId);

	boolean saveStudyMealTimePointDiet(Long projectId, List<StudyMealTimePointDietDto> smtpdDtoList);

}
