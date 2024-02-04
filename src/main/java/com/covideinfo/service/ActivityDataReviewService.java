package com.covideinfo.service;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.ActivityDataReviewDataDto;
import com.covideinfo.dto.ActivityDataReviewDto;
import com.covideinfo.dto.RequestStatusDto;
import com.covideinfo.dto.StudyActivityDataReviewDto;

public interface ActivityDataReviewService {

	List<ActivityDataReviewDto> getActivityDataReviewVolounteerList(Long studyId, Long activityId,String dateFormat);

	List<ActivityDataReviewDto> getStudyActivityDataForReview(Long studyId, Long activityId, Long roleId, String dateFormat);
	
	List<ActivityDataReviewDataDto> getActivityDataDetails(Long activityDataId, Locale currentLocale, String dateFormat);

	RequestStatusDto acceptStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale);
	
	RequestStatusDto sendCommentsStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale);
}
