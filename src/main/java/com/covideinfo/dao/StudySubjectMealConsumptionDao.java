package com.covideinfo.dao;

import com.covideinfo.dto.StudyMealConsumptionDietDto;
import com.covideinfo.dto.StudyMealConsumptionItemsDataDto;
import com.covideinfo.dto.StudyMealsConsumptionDietDtoDetails;

public interface StudySubjectMealConsumptionDao {

	StudyMealConsumptionDietDto getStudyMealConsumptionDietDtoDetails();

	StudyMealsConsumptionDietDtoDetails getStudyMealsConsumptionDietDtoDetails(Long studyId);

	String saveStudyMealConsumptionDietData(Long userId, StudyMealConsumptionItemsDataDto smcitemData);

}
