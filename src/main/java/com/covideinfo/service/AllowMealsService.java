package com.covideinfo.service;

import java.util.List;

import com.covideinfo.dto.AllowMealsDataDto;
import com.covideinfo.dto.AllowMealsRecordsDto;
import com.covideinfo.dto.StudyMasterDto;

public interface AllowMealsService {

	List<StudyMasterDto> getStudyMasterList();

	AllowMealsRecordsDto getAllowMealsRecords(Long projectId);

	String saveAllowMealsRecord(Long userId, AllowMealsDataDto almDto);

	String inactiveAllowMealRecord(Long inactId, Long userId);

}
