package com.covideinfo.service;

import com.covideinfo.dto.StudyMealConsumptionDietDto;
import com.covideinfo.dto.StudyMealConsumptionItemsDataDto;

public interface StudySubjectMealConsumptionService {

	StudyMealConsumptionDietDto getStudyMealConsumptionDietDtoDetails();

	StudyMealConsumptionDietDto getStudyRelatedMealsConsumptionDietData(Long studyId);

	String saveStudyMealConsumptionDietData(Long userId, StudyMealConsumptionItemsDataDto smcitemData);

}
