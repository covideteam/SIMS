package com.covideinfo.dao;

import java.util.List;

import com.covideinfo.dto.CpuActivitiesDetailsDto;
import com.covideinfo.dto.DiscrepancyResponseDto;
import com.covideinfo.model.LanguageSpecificValueDetails;

public interface CpurActivityReviewDao {

	CpuActivitiesDetailsDto getCpuReviewActivityData(Long projectId, Long activityId, Long userId, Long roleId, Long languageId, String type);

	List<LanguageSpecificValueDetails> getGlobalValuesList(long parameterId, long controlTypId, Long languageId);

	String saveStaticDiscrepancyDetails(Long studyId, Long activityId, Long activityDataId, String fieldName,
			List<String> descComments, String activityType, Long userId, String parameterId);

	DiscrepancyResponseDto saveStaticDiscrepancyResponseDetails(Long desId, String currentVal, String newVal, String response,
			Long userId, String activityType);

}
