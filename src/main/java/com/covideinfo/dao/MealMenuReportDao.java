package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.MealMenuReportsDetailsDto;

public interface MealMenuReportDao {

	MealMenuReportsDetailsDto getMealMenuReportDetails();

	MealMenuReportsDetailsDto getStudyLevelMealReportDetails(Long studyId);

	MealMenuReportsDetailsDto getMealMenuItemsRecordsList(List<Long> dietMenuIds, Long userId, Long studyId);

}
