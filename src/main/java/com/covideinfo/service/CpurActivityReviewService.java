package com.covideinfo.service;

import java.util.List;

import com.covideinfo.dto.CpuActivitiesReviewDataDto;
import com.covideinfo.dto.MessageDto;

public interface CpurActivityReviewService {

	CpuActivitiesReviewDataDto getCpuReviewActivityData(Long projectId, Long activityId, Long userId, Long roleId, Long languageId, String type);

	MessageDto saveStaticDiscrepancyDetails(Long studyId, Long activityId, Long activityDataId, String fieldName,
			List<String> descComments, String activityType, Long userId, String parameterId);

	MessageDto saveStaticDiscrepancyResponseDetails(Long desId, String currentVal, String newVal, String response,
			Long userId,String activityType, SampleScheduleCalculationService sscService);

}
