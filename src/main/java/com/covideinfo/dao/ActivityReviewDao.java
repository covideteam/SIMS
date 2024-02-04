package com.covideinfo.dao;

import java.util.List;
import java.util.Locale;

import com.covideinfo.dto.ActivityDataDetailsDto;
import com.covideinfo.dto.RequestStatusDto;
import com.covideinfo.dto.ResultDto;
import com.covideinfo.dto.StudyActivityDataReviewDto;
import com.covideinfo.model.InternationalizaionLanguages;
import com.covideinfo.model.LanguageSpecificGlobalParameterDetails;
import com.covideinfo.model.LanguageSpecificValueDetails;
import com.covideinfo.model.StudyActivityData;

public interface ActivityReviewDao {

	List<StudyActivityData> getStudyActivityDataRecordsList(Long activityId);

	List<StudyActivityData> getStudyActivityDataForReview(Long studyId, Long activityId, Long roleId);
	
	ActivityDataDetailsDto getActivityDataDetails(Long activityDataId, Locale currentLocale);

	List<LanguageSpecificGlobalParameterDetails> getLanguageSpecificGlobalparameterDetails(List<Long> parmIds, InternationalizaionLanguages inalg);

	List<LanguageSpecificValueDetails> getLanguageSpecificValuesDetails(long id, long id2,
			InternationalizaionLanguages inalg);
	
	RequestStatusDto acceptStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale);
	
	RequestStatusDto sendCommentsStudyActivityData(StudyActivityDataReviewDto studyActivityData, String userName, Locale locale);
}
